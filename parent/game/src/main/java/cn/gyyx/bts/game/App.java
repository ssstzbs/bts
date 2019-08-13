package cn.gyyx.bts.game;

import cn.gyyx.bts.core.Starter;
import cn.gyyx.bts.game.net.GameEventConsumerFactory;

public class App {
	
	
	public static void main(String[] args) throws Exception{
		Starter.start("game.log", new GameEventConsumerFactory());
	}
}
