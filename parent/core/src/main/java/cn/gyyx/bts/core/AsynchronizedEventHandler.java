package cn.gyyx.bts.core;

public interface AsynchronizedEventHandler {
	void onEvent(LogicEvent logicEvent) throws Throwable;
}
