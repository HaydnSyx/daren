package com.syx.taobao.websocket.core;

import static io.netty.handler.codec.http.HttpHeaderNames.HOST;

import java.util.ArrayList;
import java.util.List;

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
import com.syx.taobao.vo.im.ImMineVo;
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
		if (req.headers().get("Upgrade") != null && "websocket".equals(req.headers().get("Upgrade").toString().toLowerCase())) {
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
			final int userId = ImChannelGroup.getUserId(channel.channel().id().asLongText());
			// 广告所有好友下线信息
			BaseImVo vo = new BaseImVo();
			vo.setMine(new ImMineVo(null, null, userId, true, null));
			vo.setType("hide");
			vo.setResultCode(0);
			websocketTextMsgHandle(channel, new TextWebSocketFrame(JsonTools.writeToOrg(vo)));
			// 移除信息
			ImChannelGroup.removeChannel(channel.channel());
			ImChannelGroup.removeMap(userId);
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
			websocketTextMsgHandle(channel, frame);
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

	@SuppressWarnings("unchecked")
	private void websocketTextMsgHandle(ChannelHandlerContext channel, WebSocketFrame frame) {
		List<Integer> friends = null;
		List<Integer> offLineFriends = new ArrayList<>();
		// 接收到的信息
		String msg = ((TextWebSocketFrame) frame).text();
		try {
			ResultVo vo = imCommonService.handleMsg(msg);
			if (vo.getStatus() == AppConstant.RESULT_STATUS_SUCCESS) {
				BaseImVo imvo = (BaseImVo) vo.getData();
				// 绑定channelId与userId
				switch (imvo.getResultCode()) {
				case AppConstant.IM_CODE_CONNECT_SUCCESS:
					ImChannelGroup.addMap(imvo.getMine().getId(), channel.channel().id().asLongText());
					friends = (List<Integer>) imvo.getData();
					// 通知
					for (int mId : friends) {
						Channel targetChannel = ImChannelGroup.getChannel(ImChannelGroup.getChannelId(mId));
						if (targetChannel == null || !targetChannel.isActive()) {
							offLineFriends.add(mId);
						} else {
							targetChannel.writeAndFlush(new TextWebSocketFrame(
									JsonTools.writeToOrg(new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, new BaseImVo(AppConstant.IM_CODE_NOTICE_ONLINE, imvo.getMine().getId())))));
						}
					}
					imvo.setData(offLineFriends);
					vo.setData(imvo);
					channel.channel().write(new TextWebSocketFrame(JsonTools.writeToOrg(vo)));
					break;
				case AppConstant.IM_CODE_NOTICE_ONLINE:
					friends = (List<Integer>) imvo.getData();
					// 通知
					for (int mId : friends) {
						Channel targetChannel = ImChannelGroup.getChannel(ImChannelGroup.getChannelId(mId));
						if (targetChannel != null && targetChannel.isActive()) {
							targetChannel.writeAndFlush(new TextWebSocketFrame(
									JsonTools.writeToOrg(new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, new BaseImVo(AppConstant.IM_CODE_NOTICE_ONLINE, imvo.getMine().getId())))));
						}
					}
					break;
				case AppConstant.IM_CODE_NOTICE_OFFLINE:
					friends = (List<Integer>) imvo.getData();
					// 通知
					for (int mId : friends) {
						Channel targetChannel = ImChannelGroup.getChannel(ImChannelGroup.getChannelId(mId));
						if (targetChannel != null && targetChannel.isActive()) {
							targetChannel.writeAndFlush(new TextWebSocketFrame(
									JsonTools.writeToOrg(new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, new BaseImVo(AppConstant.IM_CODE_NOTICE_OFFLINE, imvo.getMine().getId())))));
						}
					}
					break;
				case AppConstant.IM_CODE_NOTICE_NEWMSG:
					Channel targetChannel = ImChannelGroup.getChannel(ImChannelGroup.getChannelId(imvo.getTo().getId()));
					// 离线
					if (targetChannel == null || !targetChannel.isActive()) {
					} else {
						targetChannel.writeAndFlush(new TextWebSocketFrame(JsonTools.writeToOrg(vo)));
					}
					break;
				default:
					break;
				}
			} else {
				vo.setStatus(AppConstant.RESULT_STATUS_ERROR);
				vo.setMessage("服务器解析数据错误，请检查传递数据是否无误！ param=[" + msg + "]");
				channel.channel().write(new TextWebSocketFrame(JsonTools.writeToOrg(vo)));
			}
		} catch (BizException e) {
			e.printStackTrace();
		}
	}
}
