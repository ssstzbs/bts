package cn.gyyx.bts.core;

public enum LogicEventType {

	
	 /**
     * 客户端http请求注册进来
     */
    LOGIC_HTTPCLIENT_REGIST,
    /**
     * 收到外网服务器发过来的http协议请求
     */
    LOGIC_HTTP_REQUEST_COMMING_EVENT,
    /**
     * http客户端关闭了连接
     */
    LOGIC_HTTPCLIENT_DISCONNECT,
    /**
     * tcp建立連接
     */
    LOGIC_TCP_REQUEST_COMING_EVENT,
    /**
     * tcp建立連接
     */
    LOGIC_TCP_REQUEST_CONNECT,
    /**
     * tcp斷開連接s
     */
    LOGIC_TCP_REQUEST_DISCONNECT
	
}
