package cn.gyyx.bts.core.ctrl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import cn.gyyx.bts.core.ServerHttpChannelInitializer;
import cn.gyyx.bts.core.ctrl.misc.ConnectionInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public abstract class NetServicCtrl {
	private static final Logger logger = LoggerFactory
			.getLogger(NetServicCtrl.class);
	
	protected final EventGroupCtrl eventGroupCtrl;
	
	
	public final IPCtrl ipCtrl;
	
	private final ConnectionInfo ipv4Http;
	
	@Inject
	public NetServicCtrl(EventGroupCtrl eventGroupCtrl,IPCtrl ipCtrl) throws UnknownHostException, InterruptedException {
		this.eventGroupCtrl=eventGroupCtrl;
		this.ipCtrl=ipCtrl;
		ipv4Http=acceptIpv4HttpService();
	}
	
	protected ConnectionInfo acceptIpv4HttpService() throws UnknownHostException, InterruptedException {
		return acceptService(0, 8192, 2048, ipCtrl.getOuterHost(), new ServerHttpChannelInitializer());
	}
	
	protected final ConnectionInfo acceptService(int port,
			final int socketSendSize,final int socketRecvSize,String strHost,
			ChannelInitializer<SocketChannel> initializer)
			throws InterruptedException, UnknownHostException
	{
		InetAddress host=InetAddress.getByName(strHost);
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(eventGroupCtrl.getBossGroup(),
				eventGroupCtrl.getWorkGroup());
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.option(ChannelOption.SO_REUSEADDR,true);
		serverBootstrap.option(ChannelOption.SO_BACKLOG,400);
		serverBootstrap.option(ChannelOption.SO_KEEPALIVE,false);
		serverBootstrap.option(ChannelOption.TCP_NODELAY,true);
		serverBootstrap.option(ChannelOption.SO_RCVBUF,socketRecvSize);
		serverBootstrap.option(ChannelOption.SO_SNDBUF,socketSendSize);
		serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,false);
		serverBootstrap.childOption(ChannelOption.TCP_NODELAY,true);
		serverBootstrap.childOption(ChannelOption.SO_LINGER,0);
		serverBootstrap.childHandler(initializer);
		Channel channel=serverBootstrap.bind(host,port).sync().channel();
		InetSocketAddress socketAddress = (InetSocketAddress)channel
				.localAddress();
		// 实际的ip
		String actualIp = socketAddress.getHostString();
		// 实际的端口
		int actualPort = socketAddress.getPort();
		return new ConnectionInfo(actualIp,actualPort);
	}

	

	/**
	 * 检查远程终端是否服务
	 * 
	 * @param host
	 * @param port
	 * @return
	 * @throws UnknownHostException 
	 */
	public final boolean checkRemoteCanReach(String strHost,int port) throws InterruptedException, UnknownHostException {
		InetAddress host=InetAddress.getByName(strHost);
		try(Socket socket = new Socket())
		{
			InetSocketAddress address = new InetSocketAddress(host,port);
			logger.info(String.format("connecting remote host:%s:%d", host, port));
			socket.connect(address, 2000);
			return true;
		}
		catch(Throwable e)
		{
			logger.info(String.format("remote connect failed host:%s:%d", host, port));
			Thread.sleep(2000L);
		}
		return false;
	}

	public ConnectionInfo getIpv4Http() {
		return ipv4Http;
	}


}
