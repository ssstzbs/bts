package cn.gyyx.bts.core;

public abstract class EventConsumerFactory {
	@SuppressWarnings("rawtypes")
	public abstract EventConsumer newConsumer();
}
