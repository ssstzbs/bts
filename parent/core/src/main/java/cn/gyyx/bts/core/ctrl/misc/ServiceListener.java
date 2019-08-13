package cn.gyyx.bts.core.ctrl.misc;

import cn.gyyx.bts.core.ctrl.ServiceStateCtrl;

public interface ServiceListener {
	void serviceChanged(ServiceStateCtrl serviceStateCtrl);
}
