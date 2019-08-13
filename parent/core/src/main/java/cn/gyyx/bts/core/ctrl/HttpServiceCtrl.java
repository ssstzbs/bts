package cn.gyyx.bts.core.ctrl;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.inject.Inject;

import cn.gyyx.bts.core.HttpResponseHandler;
import cn.gyyx.bts.core.Values;
import cn.gyyx.bts.core.ctrl.misc.RunnableHttpHandler;
import io.netty.channel.Channel;

public class HttpServiceCtrl {
	
	private final HashMap<String,HttpResponseHandler> pathAndHandler=new HashMap<>();
	
	private final ExecutorService httpResponseThreadPool=Executors.newFixedThreadPool(8);
	
	public final void handleRequest(Channel channel,String path,Values values) {
		HttpResponseHandler handler=pathAndHandler.get(path);
		if(null==handler) {
			channel.close();
			return;
		}
		httpResponseThreadPool.submit(new RunnableHttpHandler(handler, path, values, channel));
	}
	
	@Inject
	public HttpServiceCtrl() {
		
	}
	
	public void registerService(String path,HttpResponseHandler handler) {
		pathAndHandler.put(path, handler);
	}

	
}
