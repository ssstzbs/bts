package cn.gyyx.bts.core;

import cn.gyyx.bts.core.ctrl.misc.Session;

/**
 * 微服务响应处理器
 * @author lishile
 *
 */
public interface MircoServiceResponseHandler {
	
	void handler(String path,Object proto,Session session);
}
