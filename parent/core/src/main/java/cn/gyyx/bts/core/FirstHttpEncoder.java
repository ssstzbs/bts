package cn.gyyx.bts.core;
 

import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;


/**
 * Created by lishile on 2016/3/3.
 */
public class FirstHttpEncoder extends ChannelOutboundHandlerAdapter {
    private static final ThreadLocal<ObjectMapper> objectMapper = new ThreadLocal<ObjectMapper>(){

		@Override
		protected ObjectMapper initialValue()
		{
			ObjectMapper tempMapper = new ObjectMapper();
			//tempMapper.configure(SerializationConfig, false);
			return tempMapper;
		}
    	
    };

    @Override
    public void write(ChannelHandlerContext ctx, Object msg,
                      ChannelPromise promise) throws Exception {
        String protoJson = null;
        String contentType="text/plain";
        String location=null;
        HttpResponseStatus state=HttpResponseStatus.OK;
        try {
        	if(msg instanceof String){
        		protoJson=(String)msg;
        	}else if(msg instanceof HttpResponseMsg){
        		contentType=((HttpResponseMsg) msg).getContentType();
        		if(msg instanceof HttpResponseStringMsg){
        			protoJson=((HttpResponseStringMsg) msg).getMsg();
        		}else if(msg instanceof HttpResponseJsonObjMsg){
        			protoJson=objectMapper.get().writeValueAsString(((HttpResponseJsonObjMsg) msg).getMsg());
        		}else if(msg instanceof HttpResponseRelocationMsg){
            		protoJson="";
            		location=((HttpResponseRelocationMsg)msg).getLocation();
            		state=HttpResponseStatus.SEE_OTHER;
            	}else{
        			throw new RuntimeException();
        		}
        	}else{
        		protoJson = objectMapper.get().writeValueAsString(msg);
        	}
        } catch (Exception e) {
            ctx.fireExceptionCaught(e);
            return;
        }
        
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
        		state,
                Unpooled.copiedBuffer(protoJson, CharsetUtil.UTF_8));
        httpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE,contentType);
        httpResponse.headers().set(HttpHeaders.Names.CONTENT_LENGTH, httpResponse.content().readableBytes());
        httpResponse.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
        if(location!=null){
        	httpResponse.headers().set(HttpHeaders.Names.LOCATION,location);
        }
        ctx.writeAndFlush(httpResponse, promise);
    }
}
