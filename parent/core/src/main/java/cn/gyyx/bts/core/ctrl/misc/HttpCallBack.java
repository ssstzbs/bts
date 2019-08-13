package cn.gyyx.bts.core.ctrl.misc;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class HttpCallBack implements Callback {
	private static final Logger logger=LoggerFactory.getLogger(HttpCallBack.class);
	public abstract void onResponseImpl(Call call, Response response) throws IOException;
	
	public final void onResponse(Call call, Response response) throws IOException{
		try{
			onResponseImpl(call,response);
		}catch(Exception e){
			logger.error("",e);
		}
		try {
			response.close();
		}catch(Exception e) {
			
		}
	}
}
