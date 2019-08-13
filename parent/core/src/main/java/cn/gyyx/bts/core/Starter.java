package cn.gyyx.bts.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.xml.DOMConfigurator;
import org.w3c.dom.Element;

public class Starter {
	
	
	public static void start(String logFileName,EventConsumerFactory factory) throws Exception{
		InputStream inputStream = Starter.class.getClassLoader().getResourceAsStream(String.format("log4j.xml"));
		byte[] byteArr = new byte[inputStream.available()];
		inputStream.read(byteArr);
		String log4jFileString = new String(byteArr, "UTF-8");
		System.setProperty("log4jLogpath", new File("").getAbsolutePath() + File.separator + logFileName);
		System.setProperty("backLog", new File("").getAbsolutePath() + File.separator + "back.log");
		InputStream is = new ByteArrayInputStream(log4jFileString.getBytes("UTF-8"));
		Element element = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is).getDocumentElement();
		DOMConfigurator.configure(element);
		GlobalFactory.logicConsumerFactory=factory;
		GlobalQueue.class.newInstance();
	}
	
	
}
