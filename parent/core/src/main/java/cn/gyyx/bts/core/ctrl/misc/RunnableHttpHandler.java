package cn.gyyx.bts.core.ctrl.misc;

import cn.gyyx.bts.core.HttpResponseHandler;
import cn.gyyx.bts.core.Values;
import io.netty.channel.Channel;

public class RunnableHttpHandler implements Runnable {

	private final HttpResponseHandler handler;
	
	private final String path;
	
	private final Values values;
	
	private final Channel channel;
	
	
	
	public RunnableHttpHandler(HttpResponseHandler handler, String path, Values values, Channel channel) {
		super();
		this.handler = handler;
		this.path = path;
		this.values = values;
		this.channel = channel;
	}



	@Override
	public void run() {
		handler.handler(path, values, channel);
	}

}
