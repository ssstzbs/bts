package cn.gyyx.bts.game.module;

import com.google.inject.Singleton;

import cn.gyyx.bts.core.ctrl.NetServicCtrl;
import cn.gyyx.bts.core.module.CoreModule;
import cn.gyyx.bts.core.world.World;
import cn.gyyx.bts.game.ctrl.GameNetServicCtrl;
import cn.gyyx.bts.game.world.WorldGame;

public class GameModule extends CoreModule {

	@Override
	protected void configChildModule() {
		bind(World.class).to(WorldGame.class).in(Singleton.class);
		bind(NetServicCtrl.class).to(GameNetServicCtrl.class).in(Singleton.class);
	}

}
