package cn.gyyx.bts.core.ctrl.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import cn.gyyx.bts.core.ctrl.HttpClientCtrl;
import cn.gyyx.bts.core.ctrl.JsonCtrl;
import cn.gyyx.bts.core.ctrl.RandomCtrl;
import cn.gyyx.bts.core.ctrl.RestProtoCtrl;
import cn.gyyx.bts.core.ctrl.ServiceStateCtrl;
import cn.gyyx.bts.core.ctrl.SystemTimeCtrl;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

public class RandomServicePicker implements IServicePicker,ServiceListener {

	private final ServiceStateCtrl serviceStateCtrl;
	
	private final RandomCtrl randomCtrl;
	
	private final HttpClientCtrl httpClientCtrl;
	
	private final HashMap<String,ServiceCache> pool=new HashMap<>();
	
	private final JsonCtrl jsonCtrl;
	
	private final SystemTimeCtrl systemTimeCtrl;
	
	private final RestProtoCtrl restProtoCtrl;
	
	public RandomServicePicker(RestProtoCtrl restProtoCtrl,
			HttpClientCtrl httpClientCtrl,
			RandomCtrl randomCtrl,
			ServiceStateCtrl serviceStateCtrl,
			JsonCtrl jsonCtrl,
			SystemTimeCtrl systemTimeCtrl) {
		this.jsonCtrl=jsonCtrl;
		this.restProtoCtrl=restProtoCtrl;
		this.systemTimeCtrl=systemTimeCtrl;
		this.httpClientCtrl=httpClientCtrl;
		this.serviceStateCtrl=serviceStateCtrl;
		this.randomCtrl=randomCtrl;
		serviceStateCtrl.registerListener(this);
	}
	
	@Override
	public void serviceChanged(ServiceStateCtrl serviceStateCtrl) {
		pool.clear();
		for(Iterator<String> iter=serviceStateCtrl.serverTypeIter();iter.hasNext();) {
			String serverType=iter.next();
			MicroService service=serviceStateCtrl.getServices(serverType);
			ArrayList<Long> avaiableList=new ArrayList<Long>();
			for(Iterator<Long> processIndexIter=service.processIndexIter();processIndexIter.hasNext();) {
				avaiableList.add(processIndexIter.next());
			}
			pool.put(serverType, new ServiceCache(avaiableList));
		}
	}
	
	
	private static class ServiceCache{
		
		private final ArrayList<Long> avaiableList;
		
		private final LongOpenHashSet recoverySet=new LongOpenHashSet();

		private ServiceCache(ArrayList<Long> avaiableList) {
			this.avaiableList=avaiableList;
		}
		public ArrayList<Long> getAvaiableList() {
			return avaiableList;
		}

		public LongOpenHashSet getRecoverySet() {
			return recoverySet;
		}
	}


	@Override
	public <T> T requestService(ServiceParams params,Class<T> backProtoClazz) {
		String serverType=params.getServerType();
		ServiceCache cache=pool.get(serverType);
		if(cache==null) {
			return null;
		}
		try {
			for(;!cache.getAvaiableList().isEmpty();) {
				int avaiableIndex=randomCtrl.nextInt(cache.getAvaiableList().size());
				Long avaiableNodeProcessIndex=cache.getAvaiableList().get(avaiableIndex);
				try {
					MircoServiceNode msn=serviceStateCtrl.getServices(serverType).getNodeByProcessIndex(avaiableNodeProcessIndex);
					String httpHost=msn.getParams().get(ZooPropertiesEnum.IPV4_HTTP_HOST);
					int httpPort=Integer.parseInt(msn.getParams().get(ZooPropertiesEnum.IPV4_HTTP_PORT));
					Map<String,String> keyAndValue=new TreeMap<>();
					keyAndValue.put("protoString", jsonCtrl.serializeToStr(params.getProto()));
					keyAndValue.put("protoClassString", params.getProto().getClass().getCanonicalName());
					keyAndValue.put("protoSecondtime", String.valueOf(systemTimeCtrl.getSysSecTime()));
					String result=httpClientCtrl.httpRequestSync(httpHost, httpPort, restProtoCtrl.getRestPath(params.getProto().getClass()), keyAndValue);
					return jsonCtrl.unSerilize(result.getBytes("UTF-8"), backProtoClazz);
				}catch(Exception e) {
					cache.getRecoverySet().add(avaiableNodeProcessIndex);
					cache.getAvaiableList().remove(avaiableNodeProcessIndex);
				}
			}
		}finally {
			if(!cache.getRecoverySet().isEmpty()) {
				cache.getAvaiableList().addAll(cache.getRecoverySet());
				cache.getRecoverySet().clear();
			}
		}
		return null;
	}

}
