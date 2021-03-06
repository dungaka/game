package com.app.dispatch;

import java.util.concurrent.ConcurrentHashMap;
import org.apache.mina.core.session.IoSession;

public class ChannelService {
	private ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<String, Channel>();
	private Channel worldChannel = new Channel("WORLD");

	public ChannelService() {
		this.channels.put(this.worldChannel.getName(), this.worldChannel);
	}

	public Channel getWorldChannel() {
		return this.worldChannel;
	}

	public Channel getAndCreate(String name) {
		Channel channel = new Channel(name);
		Channel c = this.channels.putIfAbsent(name, channel);
		if (c == null) {
			c = channel;
		}
		return c;
	}

	public Channel getChannel(String name) {
		return this.channels.get(name);
	}

	public void removeSessionFromAllChannel(IoSession session) {
		for (Channel channel : this.channels.values())
			channel.removeSession(session);
	}
}