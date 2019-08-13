package cn.gyyx.bts.core.ctrl;

import com.google.inject.Inject;

import cn.gyyx.bts.core.Server2ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ConnectCtrl {
	private final EventGroupCtrl eventGroupMrg;

	@Inject
	public ConnectCtrl(EventGroupCtrl eventGroupMrg) {
		this.eventGroupMrg=eventGroupMrg;
	}
	
	public Channel connect(String ip,int port)
	{

		Bootstrap bootstrap = new Bootstrap();

		bootstrap.channel(NioSocketChannel.class);

		bootstrap.group(eventGroupMrg.getWorkGroup());

		ChannelInitializer<SocketChannel> initializer = new Server2ClientChannelInitializer();

		bootstrap.handler(initializer);

		bootstrap
				.option(ChannelOption.SO_KEEPALIVE,false)
				.option(ChannelOption.TCP_NODELAY,true)
				.option(ChannelOption.SO_RCVBUF,
						8192)
				.option(ChannelOption.SO_SNDBUF,
						8192)
				.option(ChannelOption.SO_LINGER,0);

		try
		{
			return bootstrap.connect(ip,port).sync().channel();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
