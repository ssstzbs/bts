package cn.gyyx.bts.playerstate.module;

import com.google.inject.Singleton;

import cn.gyyx.bts.core.ctrl.NetServicCtrl;
import cn.gyyx.bts.core.module.CoreModule;
import cn.gyyx.bts.core.world.World;
import cn.gyyx.bts.playerstate.ctrl.PlayerStateNetServicCtrl;
import cn.gyyx.bts.playerstate.world.WorldPlayerState;

public class PlayerStateModule extends CoreModule {

	@Override
	protected void configChildModule() {
		bind(World.class).to(WorldPlayerState.class).in(Singleton.class);
		bind(NetServicCtrl.class).to(PlayerStateNetServicCtrl.class).in(Singleton.class);
	}

}
