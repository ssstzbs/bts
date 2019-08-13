package cn.gyyx.bts.playerstate;

import cn.gyyx.bts.core.Starter;
import cn.gyyx.bts.playerstate.net.PlayerStateEventConsumerFactory;

public class App {
	public static void main(String[] args) throws Exception{
		Starter.start("playerstate.log", new PlayerStateEventConsumerFactory());
	}
}
