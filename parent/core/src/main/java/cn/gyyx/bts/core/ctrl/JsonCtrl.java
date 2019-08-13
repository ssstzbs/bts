package cn.gyyx.bts.core.ctrl;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;


public class JsonCtrl {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public JsonCtrl() {
    	//objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
    }
    public String serializeToStr(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }
    public byte[] serialize(Object object) throws
            IOException {
        return objectMapper.writeValueAsString(object).getBytes("UTF-8");
    }

    @SuppressWarnings("unchecked")
    public <T> T unSerilize(byte[] byteArray, Class<?> clazz)
            throws IOException {
        return (T) objectMapper.readValue(byteArray, clazz);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T unSerilize(String json, Class<?> clazz)
            throws IOException {
        return (T) objectMapper.readValue(json, clazz);
    }

    public String toPrettyJson(String content){
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser=new JsonParser();
        JsonElement jsonE1=jsonParser.parse(content);
        String prettyJson=gson.toJson(jsonE1);
        return prettyJson;
    }

}
