package cn.gyyx.bts.core;

import io.netty.channel.Channel;

public interface TcpHandler {
	
	void handler(Channel channel ,Object proto);
}
