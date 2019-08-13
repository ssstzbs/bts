package cn.gyyx.bts.core;

import io.netty.channel.Channel;

public class LogicEvent {
	private LogicEventType logicEventType;
	
	private Channel channel;
	
	private Object proto;
	
	private int protoEnum;

	public LogicEventType getLogicEventType() {
		return logicEventType;
	}

	public void setLogicEventType(LogicEventType logicEventType) {
		this.logicEventType = logicEventType;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Object getProto() {
		return proto;
	}

	public void setProto(Object proto) {
		this.proto = proto;
	}

	public int getProtoEnum() {
		return protoEnum;
	}

	public void setProtoEnum(int protoEnum) {
		this.protoEnum = protoEnum;
	}
}
