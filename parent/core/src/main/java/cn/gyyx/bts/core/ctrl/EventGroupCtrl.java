package cn.gyyx.bts.core.ctrl;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.inject.Inject;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class EventGroupCtrl {
	
	private final int IO_THRED_NUM=8;
	private final EventLoopGroup bossGroup = new NioEventLoopGroup(1,
			(Runnable r) -> new Thread(r,"ACCEPT_THREAD"));
	
	private final EventLoopGroup workGroup;
	
	@Inject
	public EventGroupCtrl()
	{

		workGroup = new NioEventLoopGroup(
				IO_THRED_NUM, new ThreadFactory()
		{
			private final AtomicInteger threadIndex = new AtomicInteger(0);
			@Override
			public Thread newThread(Runnable r)
			{
				int tempIndex = threadIndex.incrementAndGet();
				return new Thread(r,String.format("IO_THREAD_%d",tempIndex));
			}
		});
	}
	
	public EventLoopGroup getBossGroup()
	{
		return bossGroup;
	}

	public EventLoopGroup getWorkGroup()
	{
		return workGroup;
	}
}
