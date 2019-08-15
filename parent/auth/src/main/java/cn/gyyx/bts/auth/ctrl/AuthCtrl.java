package cn.gyyx.bts.auth.ctrl;

import com.google.inject.Inject;

import cn.gyyx.bts.auth.json.LoginResultc;

public class AuthCtrl {

	private LoginResultc loginResultc;
	
	@Inject
	public AuthCtrl() {
		loginResultc = new LoginResultc();
		loginResultc.setCode("111");
	}
}
