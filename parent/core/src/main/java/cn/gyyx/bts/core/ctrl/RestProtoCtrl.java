package cn.gyyx.bts.core.ctrl;

import java.util.HashMap;

import com.google.inject.Inject;

import cn.gyyx.bts.core.annotation.RestProto;

public class RestProtoCtrl {

	private final HashMap<Class<?>,String> clazzAndRestPath=new HashMap<>();
	
	
	@Inject
	public RestProtoCtrl() {
		
	}
	
	public String getRestPath(Class<?> clazz) {
		String restPath=clazzAndRestPath.get(clazz);
		if(restPath!=null) {
			return restPath;
		}
		RestProto annotation=clazz.getAnnotation(RestProto.class);
		clazzAndRestPath.put(clazz, annotation.value());
		return annotation.value();
	}
	
}
