package com.syx.taobao.websocket.core;

import static io.netty.handler.codec.http.HttpHeaderNames.HOST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.service.ImCommonService;
import com.syx.taobao.util.JsonTools;
import com.syx.taobao.vo.ResultVo;
import com.syx.taobao.vo.im.BaseImVo;
import com.syx.taobao.websocket.common.ImChannelGroup;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
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

@Component("webSocketServerHandle")
@ChannelHandler.Sharable
public class WebSocketServerHandle extends SimpleChannelInboundHandler<Object> {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(WebSocketServerHandle.class);

	private WebSocketServerHandshaker handshaker;

	@Autowired
	private ImCommonService imCommonService;

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
				return;
			} else {
				handshaker.handshake(ctx.channel(), req);
				ImChannelGroup.addChannel(ctx.channel());
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
			ImChannelGroup.removeChannel(channel.channel());
			return;
		}
		// 是否是Ping消息
		if (frame instanceof PingWebSocketFrame) {
			channel.channel().write(new PongWebSocketFrame(frame.content().retain()));
			// ImChannelGroup.addChannel(channel.channel());
			return;
		}
		// 二进制消息
		if (frame instanceof BinaryWebSocketFrame) {
			throw new UnsupportedOperationException("暂不支持二进制消息");
		}
		if (frame instanceof TextWebSocketFrame) {
			// 接收到的信息
			String msg = ((TextWebSocketFrame) frame).text();
			try {
				ResultVo vo = imCommonService.handleMsg(msg);
				if (vo.getStatus() == AppConstant.RESULT_STATUS_SUCCESS) {
					BaseImVo imvo = (BaseImVo) vo.getData();
					// 绑定channelId与userId
					switch (imvo.getResultCode()) {
					case AppConstant.IM_SEND_TYPE_BIND:
						ImChannelGroup.addMap(imvo.getMine().getId(), channel.channel().id().asLongText());
						channel.channel().write(new TextWebSocketFrame(JsonTools.writeToOrg(vo)));
						break;
					case AppConstant.IM_SEND_TYPE_FRIEND:
						Channel targetChannel = ImChannelGroup.getChannel(ImChannelGroup.getChannelId(imvo.getTo().getId()));
						targetChannel.writeAndFlush(new TextWebSocketFrame(JsonTools.writeToOrg(vo)));
						break;
					default:
						break;
					}
				} else {
					vo.setMessage("服务器解析数据错误，请检查传递数据是否无误！ param=[" + msg + "]");
					channel.channel().write(new TextWebSocketFrame(JsonTools.writeToOrg(vo)));
				}
			} catch (BizException e) {
				e.printStackTrace();
			}
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
