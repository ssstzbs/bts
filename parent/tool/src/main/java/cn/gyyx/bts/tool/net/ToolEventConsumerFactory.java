package cn.gyyx.bts.tool.net;

import cn.gyyx.bts.core.EventConsumer;
import cn.gyyx.bts.core.EventConsumerFactory;
import cn.gyyx.bts.core.LogicEvent;

public class ToolEventConsumerFactory extends EventConsumerFactory {

	@Override
	public EventConsumer<LogicEvent> newConsumer() {
		return new ToolLogicConsumer();
	}

}
