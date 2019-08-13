package cn.gyyx.bts.core.ctrl;

import java.util.HashMap;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import cn.gyyx.bts.core.MircoServiceResponseHandler;
import cn.gyyx.bts.core.Utils;
import cn.gyyx.bts.core.Values;
import cn.gyyx.bts.core.ctrl.misc.Session;
import io.netty.channel.Channel;

public class MircoServiceCtrl {
	
	private final HashMap<String,MircoServiceResponseHandler> pathAndMircoServiceHandler=new HashMap<>();
	
	private final HashMap<String,Class<?>> protoClassStringAndProtoClazz=new HashMap<>();
	
	private final JsonCtrl jsonCtrl;
	
	private final RestProtoCtrl restProtoCtrl;
	@Inject
	public MircoServiceCtrl(RestProtoCtrl restProtoCtrl,
			JsonCtrl jsonCtrl) {
		this.jsonCtrl=jsonCtrl;
		this.restProtoCtrl=restProtoCtrl;
	}
	
	public void register(Class<?> protoBean,MircoServiceResponseHandler handler) {
		String path=restProtoCtrl.getRestPath(protoBean);
		Preconditions.checkNotNull(path);
		Preconditions.checkArgument(pathAndMircoServiceHandler.containsKey(path)==false);
		pathAndMircoServiceHandler.put(path, handler);
	}
	
	public void handleRequest(String path,Values values,Session session) {
		//处理请求的
		MircoServiceResponseHandler handler=pathAndMircoServiceHandler.get(path);
		if(handler==null) {
			Utils.closeQuitely(session.getChannel());
			return;
		}
		String protoString=values.getAsString("protoString");
		String protoClassString=values.getAsString("protoClassString");
		Class<?> clazz=getProtoClazz(protoClassString);
		if(clazz==null) {
			Utils.closeQuitely(session.getChannel());
			return;
		}
		Object proto=null;
		try {
			proto=jsonCtrl.unSerilize(protoString, clazz);
		} catch (Exception e) {
			Utils.closeQuitely(session.getChannel());
			return;
		}
		try {
			handler.handler(path, proto,session);
		}catch(Exception e) {
			Utils.closeQuitely(session.getChannel());
		}
	}
	
	private Class<?> getProtoClazz(String protoClassString){
		try {
			Class<?> inCacheClazz=protoClassStringAndProtoClazz.get(protoClassString);
			if(inCacheClazz!=null) {
				return inCacheClazz;
			}
			Class<?> clazz=Class.forName(protoClassString);
			protoClassStringAndProtoClazz.put(protoClassString, clazz);
			return clazz;
		} catch (Exception e) {
			return null;
		}
		
	}
	
}
