package cn.gyyx.bts.core;

import io.netty.channel.ChannelHandlerContext;

public class Server2ClientSocketDecoder extends AppSocketFrameDecoder {

	public Server2ClientSocketDecoder(int maxFrameLength) {
		super(maxFrameLength);
	}
	
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx)
	{
		ensuerLogicQueue();
		super.channelRegistered(ctx);
		long sequence = logicQueue.next();
		try
		{
			LogicEvent logicEvent = logicQueue.get(sequence);
			logicEvent.setLogicEventType(LogicEventType.LOGIC_TCP_REQUEST_CONNECT);
			logicEvent.setChannel(ctx.channel());
		}
		finally
		{
			logicQueue.publish(sequence);
		}
	}
	
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception
	{
		super.channelInactive(ctx);
		// 通知服务器客户端关闭了socket连接
		ensuerLogicQueue();
		long sequence = logicQueue.next();
		try
		{
			LogicEvent logicEvent = logicQueue.get(sequence);
			logicEvent.setChannel(ctx.channel());
			logicEvent.setLogicEventType(LogicEventType.LOGIC_TCP_REQUEST_DISCONNECT);
		}
		finally
		{
			logicQueue.publish(sequence);
		}
	}

}
