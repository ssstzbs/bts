package cn.gyyx.bts.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static io.netty.handler.codec.http.HttpMethod.POST;

/**
 * Created by lishile on 2016/3/3.
 */
public class FormPayloadDecoder extends SimpleChannelInboundHandler<HttpRequest> {



    public FormPayloadDecoder() {
        super(false);
    }

    @SuppressWarnings("deprecation")
	@Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest httpRequest)
            throws IOException, InstantiationException, IllegalAccessException {

        Map<String, List<String>> requestParameters;

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(
                httpRequest.getUri());
        requestParameters = queryStringDecoder.parameters();

        if (httpRequest.getMethod() == POST) {
        	requestParameters=new TreeMap<>();
            // Add POST parameters
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false), httpRequest);
            try {
                while (decoder.hasNext()) {
                    InterfaceHttpData httpData = decoder.next();
                    if (httpData.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                        Attribute attribute = (Attribute) httpData;
                        if (!requestParameters.containsKey(attribute.getName())) {
                            requestParameters.put(attribute.getName(),
                                    new LinkedList<String>());
                        }
                        requestParameters.get(attribute.getName()).add(
                                attribute.getValue());
                        attribute.release();
                    }
                }
            } catch (HttpPostRequestDecoder.EndOfDataDecoderException ex) {
                // Exception when the body is fully decoded, even if there
                // is still data
            }

            decoder.destroy();
        }

        Values values = new Values();
        for (Entry<String, List<String>> entry : requestParameters.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            if (value.size() == 1)
                values.put(key, value.get(0));
            else
                values.putStringList(key, value);
        }

        FullDecodedRequest decodedRequest = new FullDecodedRequest(httpRequest,
                values);

        ctx.fireChannelRead(decodedRequest);
    }

}
