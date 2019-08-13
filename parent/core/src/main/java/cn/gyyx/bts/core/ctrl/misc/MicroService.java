package cn.gyyx.bts.core.ctrl.misc;

import java.util.HashMap;
import java.util.Iterator;

import com.google.common.base.Preconditions;

/**
 * 表示一个微服务
 *
 */
public class MicroService {
	
	private final String serverType;
	
	private final HashMap<Long,MircoServiceNode> processIndexAndParams=new HashMap<>();
	
	public MicroService(String serverType) {
		this.serverType=serverType;
	}

	public String getServerType() {
		return serverType;
	}
	
	public void registerNode(HashMap<String,String> params) {
		Preconditions.checkArgument(serverType.equals(params.get(ZooPropertiesEnum.SERVER_TYPE)));
		Preconditions.checkArgument(processIndexAndParams.containsKey(Long.parseLong(params.get(ZooPropertiesEnum.PROCESS_INDEX)))==false);
		MircoServiceNode newNode=new MircoServiceNode(Long.parseLong(params.get(ZooPropertiesEnum.PROCESS_INDEX)), params);
		processIndexAndParams.put(Long.parseLong(params.get(ZooPropertiesEnum.PROCESS_INDEX)),newNode);
	}
	
	public void unRegisterNode(HashMap<String,String> params) {
		String strProcessIndex=params.get(ZooPropertiesEnum.PROCESS_INDEX);
		if(strProcessIndex==null) {
			return;
		}
		Long processIndex=Long.parseLong(strProcessIndex);
		processIndexAndParams.remove(processIndex);
	}
	
	public Iterator<Long> processIndexIter(){
		return processIndexAndParams.keySet().iterator();
	}
	
	public MircoServiceNode getNodeByProcessIndex(Long processIndex) {
		return processIndexAndParams.get(processIndex);
	}
	
	
}
