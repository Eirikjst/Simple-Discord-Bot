package com.eirikjst;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.shard.DisconnectedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

public class BotUtility {
	
	private static final Logger log = LoggerFactory.getLogger(BotUtility.class);
	
	private static IDiscordClient client = null;
	private String token;
	static final String BOT_PREFIX = "!";
	private AtomicBoolean reconnect = new AtomicBoolean(true);
	
	public BotUtility(String token) {
		if (token.equals("")) {
			throw new IllegalArgumentException("Not a valid token");
		}
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	
	public IDiscordClient getIDiscordClient() {
		return client;
	}
	
	public static void sendMessageChannel(IChannel channel, String message) {
		RequestBuffer.request(() ->{
			try {
				channel.sendMessage(message);
			} catch (DiscordException e) {
				e.printStackTrace();
			}
		});
	}
	
	public boolean login() {
		System.out.println(token);
		try {
			client = new ClientBuilder().withToken(token).login();
			client.getDispatcher().registerListener(new MessageEvent());
			//Include line for audio player
			client.getDispatcher().registerListener(new AudioEvent());
			return true;
		} catch (DiscordException e) {
			log.debug("Could not log in", e);
			return false;
		}
	}
	
	public void logout() {
		reconnect.set(false);
		try {
			client.logout();
		} catch (DiscordException e) {
			log.warn("Logout failed", e);
		}
	}
	
	@EventSubscriber
	public void onReady(ReadyEvent e) {
		log.info("Discord Bot Ready");
	}
	
	@EventSubscriber
	public void onDisconnect(DisconnectedEvent e) {
		CompletableFuture.runAsync(() -> {
			if(reconnect.get()) log.info("Reconnection bot");
			try {
				login();
			} catch (DiscordException f) {
				log.warn("Failed to reconnect bot", f);
			}
		});
	}
	
	@EventSubscriber
	public void onMessage(MessageReceivedEvent e) {
		log.debug("Message received");
	}
}
