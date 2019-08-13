package cn.gyyx.bts.core;

public class HttpResponseStringMsg extends HttpResponseMsg {

	private final String msg;
	
	public HttpResponseStringMsg(String contentType,String msg) {
		super(contentType);
		this.msg=msg;
	}

	public String getMsg() {
		return msg;
	} 

}
