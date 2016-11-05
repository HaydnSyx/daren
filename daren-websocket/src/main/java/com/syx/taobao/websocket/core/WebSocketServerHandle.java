package com.syx.taobao.websocket.core;

import static io.netty.handler.codec.http.HttpHeaderNames.HOST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

public class WebSocketServerHandle extends SimpleChannelInboundHandler<Object> {

	private Logger logger = LoggerFactory.getLogger(WebSocketServerHandle.class);

	private WebSocketServerHandshaker handshaker;

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		// Http请求
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		}
		// Websocket请求
		else if (msg instanceof WebSocketFrame) {
			handleWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}

	// 处理HTTP
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		// 升级为websocket
		if ("websocket".equals(req.headers().get("Upgrade"))) {
			WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, true);
			handshaker = wsFactory.newHandshaker(req);
			if (handshaker == null) {
				WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
			} else {
				handshaker.handshake(ctx.channel(), req);
			}
		}
		// 返回错误信息
		else {

		}
	}

	// 处理WebSocket
	private void handleWebSocketFrame(ChannelHandlerContext channel, WebSocketFrame frame) {
		// 是否是关闭链路指令
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(channel.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}
		// 是否是Ping消息
		if (frame instanceof PingWebSocketFrame) {
			channel.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		// 二进制消息
		if (frame instanceof BinaryWebSocketFrame) {
			throw new UnsupportedOperationException("暂不支持二进制消息");
		}
		if (frame instanceof TextWebSocketFrame) {
			// 接收到的信息
			String msg = ((TextWebSocketFrame) frame).text();
			logger.warn("新消息：" + msg);
			channel.channel().write(new TextWebSocketFrame("接收到了消息：[" + msg + "]"));
			return;
		}
	}

	private static String getWebSocketLocation(FullHttpRequest req) {
		String location = req.headers().get(HOST).toString();
		return "ws://" + location;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
