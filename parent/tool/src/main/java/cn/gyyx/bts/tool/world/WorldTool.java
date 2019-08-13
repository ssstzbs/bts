package cn.gyyx.bts.tool.world;

import java.util.TreeMap;

import com.google.inject.Inject;

import cn.gyyx.bts.core.HttpResponseStringMsg;
import cn.gyyx.bts.core.Values;
import cn.gyyx.bts.core.ctrl.Warpper;
import cn.gyyx.bts.core.ctrl.misc.ServerTypeEnum;
import cn.gyyx.bts.core.world.World;
import cn.gyyx.bts.tool.ctrl.ToolGameCtrl;
import io.netty.channel.Channel;

public class WorldTool extends World {

	
	
	@Inject
	public WorldTool(Warpper warpper,ToolGameCtrl authGameCtrl) throws Exception {
		super(warpper);
	}
	
	
	private void httpLogin(String path, Values values, Channel channel) {
		channel.writeAndFlush(new HttpResponseStringMsg("text/html", "askdjfhasdhfklasdf"));
	}


	@Override
	protected void registerHttpService() {
		//warpper.getHttpServiceCtrl().registerService(AuthRestPath.AUHT_LOGIN.getContext(), this::httpLogin);
	}


	@Override
	protected void registerMircoService() {
		
	}


	@Override
	protected void shutdownImpl() {
		
	}


	@Override
	protected TreeMap<String, String> constructRegisterInfo() {
		return new TreeMap<>();
	}


	@Override
	protected String getServerType() {
		return ServerTypeEnum.AUTH;
	}
	
	
	

		
}
