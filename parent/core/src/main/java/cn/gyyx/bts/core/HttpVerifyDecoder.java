package cn.gyyx.bts.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Created by lishile on 2016/3/3.
 */
public class HttpVerifyDecoder extends SimpleChannelInboundHandler<HttpObject>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        DecoderResult result = httpObject.getDecoderResult();
        if (!result.isSuccess()) {
            throw new BadRequestException(result.cause());
        }
        if(httpObject instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest)httpObject;
            ctx.fireChannelRead(httpRequest);
        }
    }
}
