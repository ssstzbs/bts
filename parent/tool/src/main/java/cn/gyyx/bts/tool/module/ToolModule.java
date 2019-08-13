package cn.gyyx.bts.tool.module;

import com.google.inject.Singleton;

import cn.gyyx.bts.core.ctrl.NetServicCtrl;
import cn.gyyx.bts.core.module.CoreModule;
import cn.gyyx.bts.core.world.World;
import cn.gyyx.bts.tool.ctrl.ToolCtrl;
import cn.gyyx.bts.tool.ctrl.ToolGameCtrl;
import cn.gyyx.bts.tool.ctrl.ToolNetServicCtrl;
import cn.gyyx.bts.tool.world.WorldTool;

public class ToolModule extends CoreModule {

	@Override
	protected void configChildModule() {
		bind(World.class).to(WorldTool.class).in(Singleton.class);
		bind(NetServicCtrl.class).to(ToolNetServicCtrl.class).in(Singleton.class);
		bind(ToolCtrl.class).in(Singleton.class);
		bind(ToolGameCtrl.class).in(Singleton.class);
	}

}
