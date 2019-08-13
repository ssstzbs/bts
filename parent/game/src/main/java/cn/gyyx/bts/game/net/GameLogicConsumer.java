package cn.gyyx.bts.game.net;

import com.google.inject.AbstractModule;

import cn.gyyx.bts.core.LogicEventConsumer;
import cn.gyyx.bts.core.LogicEventType;
import cn.gyyx.bts.core.world.World;
import cn.gyyx.bts.game.module.GameModule;
import cn.gyyx.bts.game.world.WorldGame;

public class GameLogicConsumer extends LogicEventConsumer {
	@Override
    protected void initWhenThreadStart() throws Exception {
        world = injector.getInstance(World.class);
        WorldGame worldGame = (WorldGame) world;
        logicEventMrg.put(LogicEventType.LOGIC_TCP_REQUEST_COMING_EVENT,worldGame::AS_on_tcp_request_comming);
        logicEventMrg.put(LogicEventType.LOGIC_TCP_REQUEST_CONNECT,worldGame::AS_on_tcp_connect);
        logicEventMrg.put(LogicEventType.LOGIC_TCP_REQUEST_DISCONNECT,worldGame::AS_on_tcp_disconenct);
        super.initWhenThreadStart();
    }

	@Override
	protected AbstractModule createModule() {
		return new GameModule();
	}
}
