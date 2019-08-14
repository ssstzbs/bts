package cn.gyyx.bts.auth.module;

import com.google.inject.Singleton;

import cn.gyyx.bts.auth.ctrl.AuthCtrl;
import cn.gyyx.bts.auth.ctrl.AuthGameCtrl;
import cn.gyyx.bts.auth.ctrl.AuthNetServicCtrl;
import cn.gyyx.bts.auth.json.LoginResult;
import cn.gyyx.bts.auth.world.WorldAuth;
import cn.gyyx.bts.core.ctrl.NetServicCtrl;
import cn.gyyx.bts.core.module.CoreModule;
import cn.gyyx.bts.core.world.World;

public class AuthModule extends CoreModule {

	@Override
	protected void configChildModule() {
		bind(World.class).to(WorldAuth.class).in(Singleton.class);
		bind(NetServicCtrl.class).to(AuthNetServicCtrl.class).in(Singleton.class);
		bind(AuthCtrl.class).in(Singleton.class);
		bind(AuthGameCtrl.class).in(Singleton.class);
	}
	
	private void test(){
		LoginResult loginResult=new LoginResult();
		loginResult.setId("123");
		loginResult.setCode("1");
	}
}
