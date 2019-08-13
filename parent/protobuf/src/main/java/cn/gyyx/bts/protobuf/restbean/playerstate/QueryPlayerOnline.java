package cn.gyyx.bts.protobuf.restbean.playerstate;

import cn.gyyx.bts.core.annotation.RestProto;

@RestProto("/playersate/queryplayerstate")
public class QueryPlayerOnline {
	private int a;
	private boolean b;
	private String c;
	
	public QueryPlayerOnline() {
		
	}

	public QueryPlayerOnline(int a, boolean b, String c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public boolean isB() {
		return b;
	}

	public void setB(boolean b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}
	
	
}
