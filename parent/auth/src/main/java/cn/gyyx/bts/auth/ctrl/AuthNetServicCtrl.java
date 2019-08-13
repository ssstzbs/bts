package cn.gyyx.bts.auth.ctrl;

import java.net.UnknownHostException;

import com.google.inject.Inject;

import cn.gyyx.bts.core.ServerHttpChannelInitializer;
import cn.gyyx.bts.core.ctrl.EventGroupCtrl;
import cn.gyyx.bts.core.ctrl.IPCtrl;
import cn.gyyx.bts.core.ctrl.NetServicCtrl;
import cn.gyyx.bts.core.ctrl.misc.ConnectionInfo;

public class AuthNetServicCtrl extends NetServicCtrl {

	@Inject
	public AuthNetServicCtrl(EventGroupCtrl eventGroupCtrl, IPCtrl ipCtrl)
			throws UnknownHostException, InterruptedException {
		super(eventGroupCtrl, ipCtrl);
	}
	
	@Override
	protected ConnectionInfo acceptIpv4HttpService() throws UnknownHostException, InterruptedException {
		return acceptService(10001, 8192, 2048, ipCtrl.getOuterHost(), new ServerHttpChannelInitializer());
	}
}
