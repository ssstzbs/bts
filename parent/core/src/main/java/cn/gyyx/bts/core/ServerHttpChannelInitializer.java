package cn.gyyx.bts.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class ServerHttpChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		final ChannelPipeline p = ch.pipeline();
		p.addLast(new HttpRequestDecoder());
		p.addLast(new HttpObjectAggregator(1024 * 1024));
		p.addLast(new HttpVerifyDecoder());
		p.addLast(new FormPayloadDecoder());
		p.addLast(new WebLogicThreadDecoder());
		p.addLast(new HttpResponseEncoder());
		p.addLast(new FirstHttpEncoder());
		p.addLast(new DefaultExceptionHandler());
	}

}
