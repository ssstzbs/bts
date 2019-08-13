package cn.gyyx.bts.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Parser;
import com.lmax.disruptor.RingBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DefaultSocketChannelConfig;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class AppSocketFrameDecoder extends LengthFieldBasedFrameDecoder {

	protected static final AttributeKey<Integer> SEQUENCE_ATTR_KEY=AttributeKey.valueOf("SEQUENCE");
	private static final Logger logger = LoggerFactory
			.getLogger(AppSocketFrameDecoder.class);

	protected volatile RingBuffer<LogicEvent> logicQueue = null;

	public static volatile Int2ObjectMap<Parser<?>> protoEnumAndParser=new Int2ObjectOpenHashMap<>();
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception
	{
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
			throws Exception
	{
		// 走到这里的原因有多种
		logger.debug("my be remove peer close process maully");
	}

	@Override
	protected ByteBuf extractFrame(ChannelHandlerContext ctx,ByteBuf buffer,
			int index,int length)
	{
		ensuerLogicQueue();
		ByteBuf rtBuf = buffer.slice(index,length);
		int protoEnum=rtBuf.readInt();
		Parser<?> parser=protoEnumAndParser.get(protoEnum);
		if(parser==null) {
			ctx.channel().close();
			return Unpooled.EMPTY_BUFFER;
		}
		try(ByteBufInputStream is = new ByteBufInputStream(rtBuf)){
			Object protoObj = parser.parseFrom(is);
			long sequence = logicQueue.next();
			try
			{
				LogicEvent logicEvent = logicQueue.get(sequence);
				logicEvent.setLogicEventType(LogicEventType.LOGIC_TCP_REQUEST_COMING_EVENT);
				logicEvent.setProtoEnum(protoEnum);
				logicEvent.setProto(protoObj);
				logicEvent.setChannel(ctx.channel());
			}
			finally
			{
				logicQueue.publish(sequence);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Unpooled.EMPTY_BUFFER;
	}
	
	public static int uniqueStringHash(String str) {
		int h = 0;
		for (int i = 0, len = str.length(); i < len; i++) {
			h = 31 * h + str.charAt(i);
		}
		return h;
	}

	protected void ensuerLogicQueue()
	{
		if(null == logicQueue)
		{
			logicQueue = GlobalQueue.logicQueue;
		}
	}

	public AppSocketFrameDecoder(int maxFrameLength)
	{
		super(maxFrameLength,0,4,0,4);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx)
	{

		ChannelConfig config = ctx.channel().config();

		DefaultSocketChannelConfig socketConfig = (DefaultSocketChannelConfig)config;

		socketConfig.setPerformancePreferences(0,1,2);

		socketConfig.setAllocator(PooledByteBufAllocator.DEFAULT);

		ctx.fireChannelRegistered();
		
		Attribute<Integer> sequenceIndex=ctx.channel().attr(SEQUENCE_ATTR_KEY);
		if(sequenceIndex.get()==null){
			Integer initSequence=0;
			sequenceIndex.setIfAbsent(initSequence);
		}
	}

	


}
