package cn.gyyx.bts.core;

public class HttpResponseJsonObjMsg extends HttpResponseMsg {

	private final Object msg;
	
	public HttpResponseJsonObjMsg(String contentType,Object msg) {
		super(contentType);
		this.msg=msg;
	}

	public Object getMsg() {
		return msg;
	}

}
