package cn.gyyx.bts.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class Server2ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		 ChannelPipeline pipeLine = channel.pipeline();
		 pipeLine.addLast(new Server2ClientSocketDecoder(2048));
	     pipeLine.addLast(new AppLastDecoder());
	     pipeLine.addLast(new SocketEncoder());
	}
	
}
