package cn.gyyx.bts.core;

import io.netty.channel.Channel;

public interface HttpResponseHandler {
	void handler(String path,Values values,Channel channel);
}
