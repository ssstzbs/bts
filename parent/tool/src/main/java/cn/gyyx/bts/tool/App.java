package cn.gyyx.bts.tool;

import cn.gyyx.bts.core.Starter;
import cn.gyyx.bts.tool.net.ToolEventConsumerFactory;

public class App {

	public static void main(String[] args) throws Exception{
		Starter.start("tool.log", new ToolEventConsumerFactory());
	}
}
