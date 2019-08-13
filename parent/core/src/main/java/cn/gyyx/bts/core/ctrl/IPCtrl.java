package cn.gyyx.bts.core.ctrl;

import com.google.inject.Inject;

public class IPCtrl {
	
	private final String innerHost="127.0.0.1";
	
	private final String outerHost="127.0.0.1";
	
	@Inject
	public IPCtrl() {
		
	}

	public String getInnerHost() {
		return innerHost;
	}
 

	public String getOuterHost() {
		return outerHost;
	}
 
}
