package cn.gyyx.bts.core;

import io.netty.channel.Channel;

public abstract class Utils {

	public static int uniqueStringHash(String str) {
		int h = 0;
		for (int i = 0, len = str.length(); i < len; i++) {
			h = 31 * h + str.charAt(i);
		}
		return h;
	}
	
	public static void closeQuitely(AutoCloseable autoCloseable) {
		try {
			autoCloseable.close();
		}catch(Exception e) {
			
		}
	}
	
	public static void closeQuitely(Channel channel) {
		try {
			channel.close();
		}catch(Exception e) {
			
		}
	}
	
}
