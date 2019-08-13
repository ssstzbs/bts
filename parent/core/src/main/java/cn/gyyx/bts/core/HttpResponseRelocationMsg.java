package cn.gyyx.bts.core;

public class HttpResponseRelocationMsg extends HttpResponseMsg {
	private final String location;
	public HttpResponseRelocationMsg(String contentType,String location) {
		super(contentType);
		this.location=location;
	}
	public String getLocation() {
		return location;
	}

}
