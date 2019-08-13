package cn.gyyx.bts.core.ctrl;

import com.google.inject.Inject;

public class ZooPathCtrl {

	@Inject
	public ZooPathCtrl() {
		
	}

	public String getGlobalLockPath() {
		return "/global/lock";
	}
	
	public String getServerParent() {
		return "/onlineserver";
	}
	
	public String getOnlinePath(long processIndex) {
		return String.format("%s/%s", getServerParent(),processIndex+"");
	}
	
	public String getGuidPath() {
		return "/guid";
	}
}
