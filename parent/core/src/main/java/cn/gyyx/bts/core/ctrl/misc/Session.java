package cn.gyyx.bts.core.ctrl.misc;

import cn.gyyx.bts.core.HttpResponseJsonObjMsg;
import io.netty.channel.Channel;

public class Session {
	
	private final Channel channel;
	
	private final int connectSecondTime;
	
	public Session(Channel channel,int connectSecondTime) {
		this.channel=channel;
		this.connectSecondTime=connectSecondTime;
	}

	public Channel getChannel() {
		return channel;
	}

	public int getConnectSecondTime() {
		return connectSecondTime;
	}
	
	public void send(Object proto) {
		channel.writeAndFlush(new HttpResponseJsonObjMsg("text/json", proto));
	}
	
}
