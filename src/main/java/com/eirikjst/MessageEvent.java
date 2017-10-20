package com.eirikjst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class MessageEvent {
	
	private Map<String, Command> commands;
	private MessageCommands mc = new MessageCommands();
	
	public MessageEvent() {
		commands = mc.getMap();
	}
	
	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!e.getMessage().getContent().startsWith(BotUtility.BOT_PREFIX)) {
			return;
		} else {
			String[] args = e.getMessage().getContent().split(" ", 2);
			if (args.length == 0) return;
			
			String commandArg = args[0].substring(1);
			List<String> argsList = new ArrayList<>(Arrays.asList(args));
			argsList.remove(0);
			
			if (commands.containsKey(commandArg)) {
				commands.get(commandArg).runCommand(e, argsList);
			}
		}
	}
}
