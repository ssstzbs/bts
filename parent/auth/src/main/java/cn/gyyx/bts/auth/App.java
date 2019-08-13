package cn.gyyx.bts.auth;

import cn.gyyx.bts.auth.net.AuthEventConsumerFactory;
import cn.gyyx.bts.core.Starter;

public class App {

	
	public static void main(String[] args) throws Exception{
		Starter.start("auth.log", new AuthEventConsumerFactory());
	}
}
