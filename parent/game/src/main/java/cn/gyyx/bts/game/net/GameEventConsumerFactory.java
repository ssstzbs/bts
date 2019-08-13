package cn.gyyx.bts.game.net;

import cn.gyyx.bts.core.EventConsumer;
import cn.gyyx.bts.core.EventConsumerFactory;
import cn.gyyx.bts.core.LogicEvent;

public class GameEventConsumerFactory extends EventConsumerFactory {

	@Override
	public EventConsumer<LogicEvent> newConsumer() {
		return new GameLogicConsumer();
	}

}
