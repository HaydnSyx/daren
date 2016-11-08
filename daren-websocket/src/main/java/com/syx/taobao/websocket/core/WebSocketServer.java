package com.syx.taobao.websocket.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

@Component("webSocketServer")
public class WebSocketServer {

	private Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	@Autowired
	private WebSocketServerHandle webSocketServerHandle;

	public void run(int port) {
		EventLoopGroup boosGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(boosGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pip = ch.pipeline();
					pip.addLast("http-codec", new HttpServerCodec());
					pip.addLast("aggregator", new HttpObjectAggregator(65536));
					pip.addLast("http-chunked", new ChunkedWriteHandler());
					pip.addLast("handler", webSocketServerHandle);
				}
			});
			Channel ch = b.bind(port).sync().channel();
			logger.warn("netty websocket start at port [" + port + "]");
			ch.closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("netty websocket failed 。。。", e);
		} finally {
			boosGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}
