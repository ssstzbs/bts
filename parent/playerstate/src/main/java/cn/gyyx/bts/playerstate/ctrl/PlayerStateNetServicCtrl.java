package cn.gyyx.bts.playerstate.ctrl;

import java.net.UnknownHostException;

import com.google.inject.Inject;

import cn.gyyx.bts.core.ctrl.EventGroupCtrl;
import cn.gyyx.bts.core.ctrl.IPCtrl;
import cn.gyyx.bts.core.ctrl.NetServicCtrl;

public class PlayerStateNetServicCtrl extends NetServicCtrl {

	
	@Inject
	public PlayerStateNetServicCtrl(EventGroupCtrl eventGroupCtrl, IPCtrl ipCtrl)
			throws UnknownHostException, InterruptedException {
		super(eventGroupCtrl, ipCtrl);
	}

}
