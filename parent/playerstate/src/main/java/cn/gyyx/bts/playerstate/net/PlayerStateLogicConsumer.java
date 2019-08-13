package cn.gyyx.bts.playerstate.net;

import com.google.inject.AbstractModule;

import cn.gyyx.bts.core.LogicEventConsumer;
import cn.gyyx.bts.core.LogicEventType;
import cn.gyyx.bts.core.world.World;
import cn.gyyx.bts.playerstate.module.PlayerStateModule;
import cn.gyyx.bts.playerstate.world.WorldPlayerState;

public class PlayerStateLogicConsumer extends LogicEventConsumer {
	@Override
    protected void initWhenThreadStart() throws Exception {
        world = injector.getInstance(World.class);
        WorldPlayerState worldAuth = (WorldPlayerState) world;
        logicEventMrg.put(LogicEventType.LOGIC_HTTPCLIENT_REGIST,worldAuth::AS_on_httpclient_regist);
        logicEventMrg.put(LogicEventType.LOGIC_HTTPCLIENT_DISCONNECT,worldAuth::AS_on_httpclient_disconnect);
        logicEventMrg.put(LogicEventType.LOGIC_HTTP_REQUEST_COMMING_EVENT,worldAuth::AS_on_http_request);
        super.initWhenThreadStart();
    }

	@Override
	protected AbstractModule createModule() {
		return new PlayerStateModule();
	}
}
