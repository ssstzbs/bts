package cn.gyyx.bts.playerstate.world;

import java.util.TreeMap;

import com.google.inject.Inject;

import cn.gyyx.bts.core.LogicEvent;
import cn.gyyx.bts.core.Timer;
import cn.gyyx.bts.core.TimerCallBackFunc;
import cn.gyyx.bts.core.ctrl.Warpper;
import cn.gyyx.bts.core.ctrl.misc.ServerTypeEnum;
import cn.gyyx.bts.core.ctrl.misc.Session;
import cn.gyyx.bts.core.world.World;
import cn.gyyx.bts.protobuf.restbean.playerstate.QueryPlayerOnline;
import cn.gyyx.bts.protobuf.restbean.playerstate.QueryPlayerOnlineResult;
import io.netty.channel.Channel;

public class WorldPlayerState extends World {

	@Inject
	public WorldPlayerState(Warpper warpper) throws Exception {
		super(warpper);
	}
	
	 
	
	@Override
	protected void registerHttpService() {
		
	}

	@Override
	protected void registerMircoService() {
		warpper.getMircoServiceCtrl().register(QueryPlayerOnline.class, this::queryPlayerOnlineState);
	}
	
	private void queryPlayerOnlineState(String path,Object proto,Session session) {
		session.send(new QueryPlayerOnlineResult(123));
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
		return ServerTypeEnum.PLAYER_STATE;
	}

		
}
