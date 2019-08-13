package cn.gyyx.bts.game.ctrl;

import java.net.UnknownHostException;

import com.google.inject.Inject;

import cn.gyyx.bts.core.Server2ClientChannelInitializer;
import cn.gyyx.bts.core.ctrl.EventGroupCtrl;
import cn.gyyx.bts.core.ctrl.IPCtrl;
import cn.gyyx.bts.core.ctrl.NetServicCtrl;
import cn.gyyx.bts.core.ctrl.misc.ConnectionInfo;

public class GameNetServicCtrl extends NetServicCtrl {

	private final ConnectionInfo ipv4;
	
	private final ConnectionInfo ipv6;
	
	@Inject
	public GameNetServicCtrl(EventGroupCtrl eventGroupCtrl, IPCtrl ipCtrl)
			throws UnknownHostException, InterruptedException {
		super(eventGroupCtrl, ipCtrl);
		ipv4=acceptService(0, 8192, 2048, ipCtrl.getOuterHost(), new Server2ClientChannelInitializer());
		ipv6=acceptService(0, 8192, 2048, ipCtrl.getOuterHost(), new Server2ClientChannelInitializer());
	}

	public ConnectionInfo getIpv4() {
		return ipv4;
	}

	public ConnectionInfo getIpv6() {
		return ipv6;
	}

}
