package com.syx.taobao.websocket;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.syx.taobao.websocket.core.WebSocketServer;

public class NettyMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext wac = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		WebSocketServer websocket = (WebSocketServer) wac.getBean("webSocketServer");
		websocket.run(8888);
	}
}
