package cn.gyyx.bts.core;

import java.util.EnumMap;
import java.util.Map;

import cn.gyyx.bts.core.world.World;

public abstract class LogicEventConsumer extends EventConsumer<LogicEvent> {
	
	protected final Map<LogicEventType,AsynchronizedEventHandler> logicEventMrg = new EnumMap<>(
			LogicEventType.class);
	
	protected World world;
	
	
	@Override
	public void loop() {
		world.tick();
	}

	@Override
	protected void initWhenThreadStart() throws Exception {
		
	}
	
	@Override
	protected final void onEvent(LogicEvent event,long sequence,
			boolean endOfBatch) throws Throwable
	{
		LogicEventType logicEventType = event.getLogicEventType();
		AsynchronizedEventHandler handler = logicEventMrg.get(logicEventType);
		if(null == handler)
		{
			throw new NullPointerException(
					"opreation exception , no asynchronizedEventHandler matchs the logic event type: "
							+ logicEventType.toString());
		}
		handler.onEvent(event);
	}

}
