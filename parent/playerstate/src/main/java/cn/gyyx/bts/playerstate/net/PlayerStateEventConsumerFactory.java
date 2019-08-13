package cn.gyyx.bts.playerstate.net;


import cn.gyyx.bts.core.EventConsumer;
import cn.gyyx.bts.core.EventConsumerFactory;
import cn.gyyx.bts.core.LogicEvent;

public class PlayerStateEventConsumerFactory extends EventConsumerFactory {

	@Override
	public EventConsumer<LogicEvent> newConsumer() {
		return new PlayerStateLogicConsumer();
	}

}
