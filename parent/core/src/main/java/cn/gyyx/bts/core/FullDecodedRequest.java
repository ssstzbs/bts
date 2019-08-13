package cn.gyyx.bts.core;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * Created by lishile on 2016/3/3.
 */
public class FullDecodedRequest {


    private final Values values;
    
    private final String path;

    public FullDecodedRequest(HttpRequest request, Values values) {
    	QueryStringDecoder queryStringDecoder = new QueryStringDecoder(
                request.getUri());
    	path=queryStringDecoder.path();
        this.values = values;
    }

    public Values getValues() {
        return values;
    }
    
    public String getPath() {
        return path;
    }
    
}
