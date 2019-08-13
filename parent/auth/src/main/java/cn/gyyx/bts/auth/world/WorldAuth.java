package cn.gyyx.bts.auth.world;

import java.util.TreeMap;

import com.google.inject.Inject;

import cn.gyyx.bts.auth.ctrl.AuthGameCtrl;
import cn.gyyx.bts.core.HttpResponseStringMsg;
import cn.gyyx.bts.core.Values;
import cn.gyyx.bts.core.ctrl.Warpper;
import cn.gyyx.bts.core.ctrl.misc.ServerTypeEnum;
import cn.gyyx.bts.core.world.World;
import io.netty.channel.Channel;

public class WorldAuth extends World {

	
	
	@Inject
	public WorldAuth(Warpper warpper,AuthGameCtrl authGameCtrl) throws Exception {
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
