package cn.gyyx.bts.tool.ctrl;

import java.net.UnknownHostException;

import com.google.inject.Inject;

import cn.gyyx.bts.core.ServerHttpChannelInitializer;
import cn.gyyx.bts.core.ctrl.EventGroupCtrl;
import cn.gyyx.bts.core.ctrl.IPCtrl;
import cn.gyyx.bts.core.ctrl.NetServicCtrl;
import cn.gyyx.bts.core.ctrl.misc.ConnectionInfo;

public class ToolNetServicCtrl extends NetServicCtrl {

	@Inject
	public ToolNetServicCtrl(EventGroupCtrl eventGroupCtrl, IPCtrl ipCtrl)
			throws UnknownHostException, InterruptedException {
		super(eventGroupCtrl, ipCtrl);
	}
	
	@Override
	protected ConnectionInfo acceptIpv4HttpService() throws UnknownHostException, InterruptedException {
		return acceptService(10001, 8192, 2048, ipCtrl.getOuterHost(), new ServerHttpChannelInitializer());
	}
}
