package cn.gyyx.bts.auth.net;

import com.google.inject.AbstractModule;

import cn.gyyx.bts.auth.module.AuthModule;
import cn.gyyx.bts.auth.world.WorldAuth;
import cn.gyyx.bts.core.LogicEventConsumer;
import cn.gyyx.bts.core.LogicEventType;
import cn.gyyx.bts.core.world.World;

public class AuthLogicConsumer extends LogicEventConsumer {
	@Override
    protected void initWhenThreadStart() throws Exception {
        world = injector.getInstance(World.class);
        WorldAuth worldAuth = (WorldAuth) world;
        logicEventMrg.put(LogicEventType.LOGIC_HTTPCLIENT_REGIST,worldAuth::AS_on_httpclient_regist);
        logicEventMrg.put(LogicEventType.LOGIC_HTTPCLIENT_DISCONNECT,worldAuth::AS_on_httpclient_disconnect);
        logicEventMrg.put(LogicEventType.LOGIC_HTTP_REQUEST_COMMING_EVENT,worldAuth::AS_on_http_request);
        super.initWhenThreadStart();
    }

	@Override
	protected AbstractModule createModule() {
		return new AuthModule();
	}
}
