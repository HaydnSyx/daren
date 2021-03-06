package com.syx.taobao.websocket.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syx.taobao.util.StringUtil;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ImChannelGroup {

	private static Logger logger = LoggerFactory.getLogger(ImChannelGroup.class);

	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	public static void addMap(int userId, String channelId) {
		map.put(userId, channelId);
		logger.warn("Add! map=[" + map + "]");
	}

	public static void removeMap(int userId) {
		map.remove(userId);
		logger.warn("Remove! map=[" + map + "]");
	}

	public static void updateMap(int userId, Channel channel) {
		Channel old = getChannel(getChannelId(userId));
		if (old != null) {
			removeChannel(old);
		}
		addChannel(channel);
		addMap(userId, channel.id().asLongText());
	}

	public static String getChannelId(int userId) {
		return map.isEmpty() || StringUtil.isNull(map.get(userId)) ? "" : map.get(userId);
	}

	public static void addChannel(Channel channel) {
		group.add(channel);
	}

	public static void removeChannel(Channel channel) {
		group.remove(channel);
	}

	public static int getUserId(String channelId) {
		int userId = 0;
		if (!map.isEmpty()) {
			for (int key : map.keySet()) {
				if (map.get(key).equals(channelId)) {
					userId = key;
					break;
				}
			}
		}
		return userId;
	}

	public static Channel getChannel(String channelId) {
		if (StringUtil.isNull(channelId)) {
			return null;
		}
		for (Iterator<Channel> iterator = group.iterator(); iterator.hasNext();) {
			Channel channel = (Channel) iterator.next();
			if (channelId.equals(channel.id().asLongText())) {
				return channel;
			}

		}
		return null;
	}

	public static boolean vailChannelActive(int userId) {
		Channel channel = getChannel(getChannelId(userId));
		return channel == null ? false : channel.isActive();
	}

	public static boolean vailChannelOpen(int userId) {
		Channel channel = getChannel(getChannelId(userId));
		return channel == null ? false : channel.isOpen();
	}

	public static boolean vailChannelWritable(int userId) {
		Channel channel = getChannel(getChannelId(userId));
		return channel == null ? false : channel.isWritable();
	}
}
