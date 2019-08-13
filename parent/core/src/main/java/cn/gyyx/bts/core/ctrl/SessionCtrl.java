package cn.gyyx.bts.core.ctrl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.Inject;

import cn.gyyx.bts.core.Timer;
import cn.gyyx.bts.core.Utils;
import cn.gyyx.bts.core.ctrl.misc.Session;
import io.netty.channel.Channel;

public class SessionCtrl {

	private final Map<Channel,Session> channelAndSession=new HashMap<>();

	private final SystemTimeCtrl systemTimeCtrl;
	
	@Inject
	public SessionCtrl(TimerCtrl timerCtrl,SystemTimeCtrl systemTimeCtrl) {
		this.systemTimeCtrl=systemTimeCtrl;
		timerCtrl.addTimer(new Timer(3000,Integer.MAX_VALUE,this::closeTimeoutSessionTimer) {
		});
	}
	
	public void registerSession(Session session) {
		channelAndSession.put(session.getChannel(), session);
	}
	
	private void closeTimeoutSessionTimer(Timer timer) {
		
		for(Iterator<Entry<Channel, Session>> iter=channelAndSession.entrySet().iterator();iter.hasNext();) {
			Entry<Channel, Session> pair=iter.next();
			Session oneSession=pair.getValue();
			if(oneSession.getConnectSecondTime()+30000<systemTimeCtrl.getSysSecTime()) {
				iter.remove();
				Utils.closeQuitely(oneSession.getChannel());
				continue;
			}
		}
	}
	
	public Session getSession(Channel channel) {
		return channelAndSession.get(channel);
	}
	
	
	
}
