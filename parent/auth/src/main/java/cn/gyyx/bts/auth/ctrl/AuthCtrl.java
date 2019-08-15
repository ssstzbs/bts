package cn.gyyx.bts.auth.ctrl;

import com.google.inject.Inject;

import cn.gyyx.bts.auth.json.LoginResultb;

public class AuthCtrl {

	private LoginResultb loginResultb;
	
	@Inject
	public AuthCtrl() {
		loginResultb = new LoginResultb();
		loginResultb.setCode("111");
		loginResultb.setLevel(43343);
		loginResultb.setSilver(2323);
		loginResultb.setGold(3);
	}
}
