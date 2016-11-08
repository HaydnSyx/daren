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
}
