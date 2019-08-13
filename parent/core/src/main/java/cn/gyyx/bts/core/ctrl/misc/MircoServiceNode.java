package cn.gyyx.bts.core.ctrl.misc;

import java.util.HashMap;

public class MircoServiceNode {
	
	private final long processIndex;
	
	private final HashMap<String,String> params;
	
	public MircoServiceNode(long processIndex,HashMap<String,String> params) {
		this.processIndex=processIndex;
		this.params=params;
	}
	public long getProcessIndex() {
		return processIndex;
	}
	public HashMap<String,String> getParams() {
		return params;
	}
	
}
