package cn.gyyx.bts.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AppLastDecoder extends ChannelInboundHandlerAdapter {
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //这里什么都不做,不要将消息继续派发,作为收包的结束派发handler
    }
}
