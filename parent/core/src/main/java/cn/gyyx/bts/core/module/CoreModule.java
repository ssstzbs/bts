package cn.gyyx.bts.core.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import cn.gyyx.bts.core.ctrl.ConnectCtrl;
import cn.gyyx.bts.core.ctrl.EventGroupCtrl;
import cn.gyyx.bts.core.ctrl.GuidCtrl;
import cn.gyyx.bts.core.ctrl.HttpClientCtrl;
import cn.gyyx.bts.core.ctrl.HttpServiceCtrl;
import cn.gyyx.bts.core.ctrl.JsonCtrl;
import cn.gyyx.bts.core.ctrl.ProcessIndexCtrl;
import cn.gyyx.bts.core.ctrl.RandomCtrl;
import cn.gyyx.bts.core.ctrl.RestCtrl;
import cn.gyyx.bts.core.ctrl.RestProtoCtrl;
import cn.gyyx.bts.core.ctrl.ServiceStateCtrl;
import cn.gyyx.bts.core.ctrl.SessionCtrl;
import cn.gyyx.bts.core.ctrl.SystemTimeCtrl;
import cn.gyyx.bts.core.ctrl.TimerCtrl;
import cn.gyyx.bts.core.ctrl.Warpper;
import cn.gyyx.bts.core.ctrl.ZooCtrl;
import cn.gyyx.bts.core.ctrl.ZooPathCtrl;

public abstract class CoreModule extends AbstractModule{

	private void configCoreModule() {
		bind(GuidCtrl.class).in(Singleton.class);
		bind(EventGroupCtrl.class).in(Singleton.class);
		bind(Warpper.class).in(Singleton.class);
		bind(SystemTimeCtrl.class).in(Singleton.class);
		bind(TimerCtrl.class).in(Singleton.class);
		bind(ConnectCtrl.class).in(Singleton.class);
		bind(ZooCtrl.class).in(Singleton.class);
		bind(JsonCtrl.class).in(Singleton.class);
		bind(ZooPathCtrl.class).in(Singleton.class);
		bind(ProcessIndexCtrl.class).in(Singleton.class);
		bind(RandomCtrl.class).in(Singleton.class);
		bind(HttpClientCtrl.class).in(Singleton.class);
		bind(HttpServiceCtrl.class).in(Singleton.class);
		bind(ServiceStateCtrl.class).in(Singleton.class);
		bind(RestCtrl.class).in(Singleton.class);
		bind(RestProtoCtrl.class).in(Singleton.class);
		bind(SessionCtrl.class).in(Singleton.class);
	}
	
	@Override
	protected void configure() {
		configCoreModule();
		configChildModule();
	}
	
	protected abstract void configChildModule();

}
