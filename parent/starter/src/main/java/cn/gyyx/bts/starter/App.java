package cn.gyyx.bts.starter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.xml.DOMConfigurator;
import org.w3c.dom.Element;

import cn.gyyx.bts.auth.net.AuthEventConsumerFactory;
import cn.gyyx.bts.core.GlobalFactory;
import cn.gyyx.bts.core.GlobalQueue;
import cn.gyyx.bts.game.net.GameEventConsumerFactory;
import cn.gyyx.bts.playerstate.net.PlayerStateEventConsumerFactory;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		InputStream inputStream = App.class.getClassLoader().getResourceAsStream(String.format("log4j.xml"));
		byte[] byteArr = new byte[inputStream.available()];
		inputStream.read(byteArr);
		String log4jFileString = new String(byteArr, "UTF-8");
		String logFileName = "";
		String serverType = args[0];
		switch (serverType) {
		case "auth": {
			GlobalFactory.logicConsumerFactory=new AuthEventConsumerFactory();
			logFileName = "auth.log";
			break;
		}
		case "game": {
			GlobalFactory.logicConsumerFactory=new GameEventConsumerFactory();
			logFileName = "game.log";
			break;
		}
		case "playerstate":{
			GlobalFactory.logicConsumerFactory=new PlayerStateEventConsumerFactory();
			logFileName = "playerstate.log";
			break;
		}
		default: {
			throw new RuntimeException();
		}
		}
		System.setProperty("log4jLogpath", new File("").getAbsolutePath() + File.separator + logFileName);
		System.setProperty("backLog", new File("").getAbsolutePath() + File.separator + "back.log");
		InputStream is = new ByteArrayInputStream(log4jFileString.getBytes("UTF-8"));
		Element element = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is).getDocumentElement();
		DOMConfigurator.configure(element);
		GlobalQueue.class.newInstance();
	}
}
