package cn.gyyx.bts.auth.net;

import cn.gyyx.bts.core.EventConsumer;
import cn.gyyx.bts.core.EventConsumerFactory;
import cn.gyyx.bts.core.LogicEvent;

public class AuthEventConsumerFactory extends EventConsumerFactory {

	@Override
	public EventConsumer<LogicEvent> newConsumer() {
		return new AuthLogicConsumer();
	}

}
