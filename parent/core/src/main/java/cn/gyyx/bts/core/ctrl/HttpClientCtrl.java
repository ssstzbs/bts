package cn.gyyx.bts.core.ctrl;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClientCtrl {

	private final OkHttpClient client;
	@Inject
	public HttpClientCtrl() {
		client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).build();
	}

	public String httpRequestSync(String host, int port, String path, Map<String, String> params)
			throws URISyntaxException, IOException {
		FormBody.Builder formBodyBuilder=new FormBody.Builder();
		for(String key:params.keySet()){
			formBodyBuilder.add(key, params.get(key));
		}
		FormBody formBody=formBodyBuilder.build();
		Request request=new Request.Builder().url("http://"+host+":"+port+path).post(formBody).build();
		try(Response response=client.newCall(request).execute()){
			return response.body().string();
		}
	}
	

}
