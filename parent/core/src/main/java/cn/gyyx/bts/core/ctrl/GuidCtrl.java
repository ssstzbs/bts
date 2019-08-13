package cn.gyyx.bts.core.ctrl;

import org.apache.zookeeper.CreateMode;

import com.google.inject.Inject;

public class GuidCtrl {
	
	private int guidIndex;
	
	private int guidSequence=Integer.MAX_VALUE;
	
	private final ZooCtrl zooCtrl;
	
	private final ZooPathCtrl zooPathMrg;
	
	@Inject
	public GuidCtrl(ZooCtrl zooCtrl,ZooPathCtrl zooPathMrg) throws Exception {
		this.zooCtrl=zooCtrl;
		this.zooPathMrg=zooPathMrg;
		generateGuid();
	}
	
	public long generateGuid(){
		try {
			if(guidSequence==Integer.MAX_VALUE) {
				guidSequence=1;
				guidIndex=getZooGuid();
			}
			++guidSequence;
			long high=((long)guidIndex)<<32;
			long low=guidSequence;
			return high|low;
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	private int getZooGuid() throws Exception {
		zooCtrl.lock();
		try {
			if(!zooCtrl.checkExist(zooPathMrg.getGuidPath())) {
				zooCtrl.createPath(zooPathMrg.getGuidPath(), "1".getBytes("UTF-8"), CreateMode.PERSISTENT);
			}
			int guidIndex=Integer.parseInt(new String(zooCtrl.getData(zooPathMrg.getGuidPath()),"UTF-8"));
			zooCtrl.setData(zooPathMrg.getGuidPath(), String.valueOf(guidIndex+1).getBytes("UTF-8"));
			return guidIndex;
		}finally {
			zooCtrl.unLock();
		}
	}
}
