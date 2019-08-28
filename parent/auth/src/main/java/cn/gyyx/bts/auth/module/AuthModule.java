package cn.gyyx.bts.auth.module;

import com.google.inject.Singleton;

import cn.gyyx.bts.auth.ctrl.AuthCtrl;
import cn.gyyx.bts.auth.ctrl.AuthGameCtrl;
import cn.gyyx.bts.auth.ctrl.AuthNetServicCtrl;
import cn.gyyx.bts.auth.json.LoginResultb;
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
		LoginResultb loginResultb=new LoginResultb();
		loginResultb.setId("123567-88");
		loginResultb.setCode("11");
		loginResultb.setLevel(1223);
		loginResultb.setGold(1345);
		loginResultb.setExp(1456);
		loginResultb.setSilver(1567);
		
		//commit 1
		//
		//
		//
	}
}
