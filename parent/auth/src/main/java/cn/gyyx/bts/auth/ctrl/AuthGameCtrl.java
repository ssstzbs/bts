package cn.gyyx.bts.auth.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import com.google.inject.Inject;

import cn.gyyx.bts.core.ctrl.JsonCtrl;
import cn.gyyx.bts.core.ctrl.RandomCtrl;
import cn.gyyx.bts.core.ctrl.ZooCtrl;
import cn.gyyx.bts.core.ctrl.ZooPathCtrl;

public class AuthGameCtrl {

	private final PathChildrenCache node;
	
	private final JsonCtrl jsonCtrl;
	
	private final RandomCtrl randCtrl;
	
	private final ArrayList<GameInfo> gameList = new ArrayList<>();

	@Inject
	public AuthGameCtrl(RandomCtrl randCtrl,
			ZooCtrl zooCtrl,
			ZooPathCtrl zooPathCtrl, 
			JsonCtrl jsonCtrl) throws Exception {
		this.jsonCtrl=jsonCtrl;
		this.randCtrl=randCtrl;
		node = new PathChildrenCache(zooCtrl.constructCurator(), zooPathCtrl.getServerParent(), true);
				node.start(StartMode.BUILD_INITIAL_CACHE);
		for (ChildData data : node.getCurrentData()) {
			HashMap<String, String> params = jsonCtrl.unSerilize(data.getData(), HashMap.class);
			if(params.get("serverType").equals("game")==false) {
				continue;
			}
			GameInfo gameInfo = new GameInfo(Long.parseLong(params.get("processIndex")), params.get("ipv4Host"),
					Integer.parseInt(params.get("ipv4Port")), params.get("ipv6Host"),
					Integer.parseInt(params.get("ipv6Port")));
			synchronized(this){
				gameList.add(gameInfo);
			}
		}
		node.getListenable().addListener(new CacheChildListener());
	}

	private class CacheChildListener implements PathChildrenCacheListener {

		@Override
		public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
			switch (event.getType()) {
			case CHILD_ADDED: {
				HashMap<String, String> params = jsonCtrl.unSerilize(event.getData().getData(), HashMap.class);
				if(params.get("serverType").equals("game")) {
					GameInfo gameInfo = new GameInfo(Long.parseLong(params.get("processIndex")), params.get("ipv4Host"),
							Integer.parseInt(params.get("ipv4Port")), params.get("ipv6Host"),
							Integer.parseInt(params.get("ipv6Port")));
					synchronized(AuthGameCtrl.this) {
						gameList.add(gameInfo);
					}
				}
				break;
			}
			case CHILD_REMOVED: {
				HashMap<String, String> params = jsonCtrl.unSerilize(event.getData().getData(), HashMap.class);
				if(params.get("serverType").equals("game")) {
					synchronized(AuthGameCtrl.this) {
						for(Iterator<GameInfo> iter=gameList.iterator();iter.hasNext();) {
							GameInfo info=iter.next();
							if(info.getProcessIndex()!=Long.parseLong(params.get("processIndex"))) {
								continue;
							}
							iter.remove();
							break;
						}
					}
				}
				break;
			}
			default: {
				;
			}
			}
		}

	}

	private static class GameInfo {
		private long processIndex;
		private String ipv4Host;
		private int ipv4Port;
		private String ipv6Host;
		private int ipv6Port;

		public GameInfo(long processIndex, String ipv4Host, int ipv4Port, String ipv6Host, int ipv6Port) {
			super();
			this.setProcessIndex(processIndex);
			this.setIpv4Host(ipv4Host);
			this.setIpv4Port(ipv4Port);
			this.setIpv6Host(ipv6Host);
			this.setIpv6Port(ipv6Port);
		}

		public String getIpv4Host() {
			return ipv4Host;
		}

		public void setIpv4Host(String ipv4Host) {
			this.ipv4Host = ipv4Host;
		}

		public int getIpv4Port() {
			return ipv4Port;
		}

		public void setIpv4Port(int ipv4Port) {
			this.ipv4Port = ipv4Port;
		}

		public int getIpv6Port() {
			return ipv6Port;
		}

		public void setIpv6Port(int ipv6Port) {
			this.ipv6Port = ipv6Port;
		}

		public String getIpv6Host() {
			return ipv6Host;
		}

		public void setIpv6Host(String ipv6Host) {
			this.ipv6Host = ipv6Host;
		}

		public long getProcessIndex() {
			return processIndex;
		}

		public void setProcessIndex(long processIndex) {
			this.processIndex = processIndex;
		}

	}
	
	
	public GameInfo allocateGame() {
		return gameList.get(randCtrl.nextInt(gameList.size()));
	}
}
