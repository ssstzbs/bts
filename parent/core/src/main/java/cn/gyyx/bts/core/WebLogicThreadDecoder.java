package cn.gyyx.bts.core;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DefaultSocketChannelConfig;

/**
 * Created by lishile on 2016/3/3.
 */
public class WebLogicThreadDecoder extends
        SimpleChannelInboundHandler<FullDecodedRequest> {


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        ChannelConfig config = ctx.channel().config();
        DefaultSocketChannelConfig socketConfig = (DefaultSocketChannelConfig)config;
        socketConfig.setPerformancePreferences(0,1,2);
        socketConfig.setAllocator(PooledByteBufAllocator.DEFAULT);
        long sequence = GlobalQueue.logicQueue.next();
        try{
            LogicEvent logicEvent = GlobalQueue.logicQueue.get(sequence);
            logicEvent.setChannel(ctx.channel());
            logicEvent.setLogicEventType(LogicEventType.LOGIC_HTTPCLIENT_REGIST);
        }finally{
            GlobalQueue.logicQueue.publish(sequence);
        }
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        // 通知服务器某个服务器进程关闭了socket连接
        long sequence = GlobalQueue.logicQueue.next();
        try{
            LogicEvent logicEvent = GlobalQueue.logicQueue.get(sequence);
            logicEvent.setChannel(ctx.channel());
            logicEvent.setLogicEventType(LogicEventType.LOGIC_HTTPCLIENT_DISCONNECT);
        }finally{
            GlobalQueue.logicQueue.publish(sequence);
        }
    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullDecodedRequest msg) throws Exception {
        long sequence = GlobalQueue.logicQueue.next();
        try
        {
            LogicEvent logicEvent = GlobalQueue.logicQueue.get(sequence);
            logicEvent.setLogicEventType(LogicEventType.LOGIC_HTTP_REQUEST_COMMING_EVENT);
            logicEvent.setProto(msg);
            logicEvent.setChannel(ctx.channel());
        }
        finally
        {
            GlobalQueue.logicQueue.publish(sequence);
        }
    }


}
