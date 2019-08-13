package cn.gyyx.bts.core;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.Builder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
public class SocketEncoder extends ChannelOutboundHandlerAdapter {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SocketEncoder.class);
	
	protected static final ThreadLocal<EncoderMrg> localEncoderMrg = new ThreadLocal<EncoderMrg>()
	{
		protected EncoderMrg initialValue()
		{
			return new EncoderMrg();
		}
	};

	
	protected static class EncoderMrg{
		private final Map<Class<?>,Integer> clazzAndProtEnum=new HashMap<>();
		
		public void register(Class<?> clazz,int hash) {
			clazzAndProtEnum.put(clazz, hash);
		}
		
		public Integer getHash(Class<?> clazz) {
			return clazzAndProtEnum.get(clazz);
		}
		
	}
	
	@Override
	public void write(ChannelHandlerContext ctx,Object msg,
			ChannelPromise promise) throws Exception
	{

		try
		{
			if(msg instanceof GeneratedMessage)
			{
				GeneratedMessage generatedMsg = (GeneratedMessage)msg;
				writeImpl(ctx,promise,generatedMsg);
			}
			else if(msg instanceof Builder)
			{
				Builder<?> builder = (Builder<?>)msg;
				GeneratedMessage generatedMsg = (GeneratedMessage)builder
						.build();
				writeImpl(ctx,promise,generatedMsg);
			}
			else
			{
			}
		}
		catch(Exception e)
		{
			logger.error("",e);
		}

		
	}
	
	protected void writeImpl(ChannelHandlerContext ctx,ChannelPromise promise,
			GeneratedMessage generatedMsg) throws Exception
	{
		EncoderMrg encoderMrg=localEncoderMrg.get();
		Integer protoEnumObj=encoderMrg.getHash(generatedMsg.getClass());
		if(protoEnumObj==null) {
			protoEnumObj=Utils.uniqueStringHash(generatedMsg.getClass().getName());
			encoderMrg.register(generatedMsg.getClass(), protoEnumObj);
		}
		int protoLen=generatedMsg.getSerializedSize();
		ByteBuf byteBuf=ctx.alloc().directBuffer(protoLen+4);
		byteBuf.writeInt(protoLen+4);
		byteBuf.writeInt(protoEnumObj.intValue());
		try(ByteBufOutputStream out = new ByteBufOutputStream(byteBuf))
		{
			generatedMsg.writeTo(out);
		}
		super.write(ctx,byteBuf,promise);
	}
}
