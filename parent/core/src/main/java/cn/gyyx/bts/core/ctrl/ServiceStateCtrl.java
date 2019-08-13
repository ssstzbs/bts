package cn.gyyx.bts.core.ctrl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import cn.gyyx.bts.core.ctrl.misc.MicroService;
import cn.gyyx.bts.core.ctrl.misc.ServiceListener;

public class ServiceStateCtrl {
	
	private static final Logger logger=LoggerFactory.getLogger(ServiceStateCtrl.class);
	
	private final List<ServiceListener> observers=new LinkedList<>();

	private final HashMap<String,MicroService> serverTypeAndServerList=new HashMap<>();
	
	private final PathChildrenCache node;
	
	private final JsonCtrl jsonCtrl;
	
	private final Queue<ZooEvent> eventQueue=new ConcurrentLinkedQueue<>();
	
	@Inject
	public ServiceStateCtrl(JsonCtrl jsonCtrl,
			ZooCtrl zooCtrl,
			ZooPathCtrl zooPathCtrl) throws Exception{
		this.jsonCtrl=jsonCtrl;
		node = new PathChildrenCache(zooCtrl.constructCurator(), zooPathCtrl.getServerParent(), true);
		node.start(StartMode.BUILD_INITIAL_CACHE);
		for (ChildData data : node.getCurrentData()) {
			regsiterNode(data.getData());
		}
		node.getListenable().addListener(new CacheChildListener());
	}
	
	private void regsiterNode(byte[] data) throws IOException {
		HashMap<String,String> params=jsonCtrl.unSerilize(data, HashMap.class);
		String serverType=params.get("serverType");
		if(!serverTypeAndServerList.containsKey(serverType)) {
			serverTypeAndServerList.put(serverType, new MicroService(serverType));
		}
		serverTypeAndServerList.get(serverType).registerNode(params);
		notifyListener();
	}
	
	private void unregisterNode(byte[] data)throws IOException{
		HashMap<String,String> params=jsonCtrl.unSerilize(data, HashMap.class);
		String serverType=params.get("serverType");
		serverTypeAndServerList.get(serverType).unRegisterNode(params);
		notifyListener();
	}
	
	private class CacheChildListener implements PathChildrenCacheListener {
		private void offerCareEvent(PathChildrenCacheEvent.Type type,byte[] data) {
			byte[] copyBytes=new byte[data.length];
			System.arraycopy(data, 0, copyBytes, 0, copyBytes.length);
			eventQueue.offer(new ZooEvent(type, copyBytes));
		}
		@Override
		public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
			switch (event.getType()) {
			case CHILD_ADDED: {
				offerCareEvent(PathChildrenCacheEvent.Type.CHILD_ADDED,event.getData().getData());
				break;
			}
			case CHILD_REMOVED: {
				offerCareEvent(PathChildrenCacheEvent.Type.CHILD_REMOVED,event.getData().getData());
				break;
			}
			default: {
				;
			}
			}
		}

	}
	
	
	private static class ZooEvent{
		private final PathChildrenCacheEvent.Type eventType;
		
		private final byte[] data;

		private ZooEvent(Type eventType, byte[] data) {
			super();
			this.eventType = eventType;
			this.data = data;
		}

		public byte[] getData() {
			return data;
		}

		public PathChildrenCacheEvent.Type getEventType() {
			return eventType;
		}
		
		
	}


	public void onSecond() {
		try {
			while(!eventQueue.isEmpty()) {
				ZooEvent zooEvent=eventQueue.poll();
				switch(zooEvent.getEventType()) {
				case CHILD_ADDED:{
					regsiterNode(zooEvent.getData());
					break;
				}
				case CHILD_REMOVED:{
					unregisterNode(zooEvent.getData());
					break;
				}
				default:{
					;
				}
				}
			}
		}catch(Exception e) {
			logger.error("",e);
		}
		
	}
	
	private void notifyListener() {
		for(ServiceListener listener:observers) {
			listener.serviceChanged(this);
		}
	}
	
	public void registerListener(ServiceListener listener) {
		observers.add(listener);
		notifyListener();
	}

	public MicroService getServices(String serverType) {
		return serverTypeAndServerList.get(serverType);
	}
	 
	public Iterator<String> serverTypeIter(){
		return serverTypeAndServerList.keySet().iterator();
	}
	
}
