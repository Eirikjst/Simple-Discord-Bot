package com.eirikjst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.impl.obj.User;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;

public class MessageCommands {
	
	private Map<String, Command> commands = new HashMap<String, Command>();
	
	public MessageCommands() {
		fillMap();
	}
	
	public Map<String, Command> getMap() {
		return commands;
	}
	
	private void fillMap() {
		ping();
		eightBall();
		craqro();
		allCommands();
		joinChannel();
		leaveChannel();
		//moveUser();
	}
	
	private void ping() {
		commands.put("ping", (event, args) -> {
			BotUtility.sendMessageChannel(event.getChannel(), "pong");
		});
	}
	
	private void eightBall() {
		String[] answer = {"It is certain", "It is decidedly so", "Without a doubt", "Yes definitely",
						   "You may rely on it", "As I see it, yes", "Most likely", "Outlook good",
						   "Yes", "Signs point to yes", "Reply hazy try again", "Ask again later",
						   "Better not tell you now", "Cannot predict now", "Concentrate and ask again", "Don't count on it",
						   "My reply is no", "My sources say no", "Outlook not so good", "Very doubtful"
						  };
		int ran = new java.util.Random().nextInt((20 - (0)) + 1) + 0;
		commands.put("8ball", (event, args) -> {
			BotUtility.sendMessageChannel(event.getChannel(), answer[ran]);
		});
	}
	
	//memes
	private void craqro() {
		String url = "https://gyazo.com/dadb32d01d182c5853af6ee6dad00259";
		String quote = "Run, Forrest! Run!";
		commands.put("craqro", (event, args) -> {
			BotUtility.sendMessageChannel(event.getChannel(), url);
			BotUtility.sendMessageChannel(event.getChannel(), quote);
		});
	}
	
	private void allCommands() {
		String allCommands = getAllCommands();
		commands.put("commands", (event, args) ->{
			BotUtility.sendMessageChannel(event.getChannel(), "All commands");
			BotUtility.sendMessageChannel(event.getChannel(), allCommands);
		});
	}
	
	private String getAllCommands() {
		String output = "";
		for (String key : commands.keySet()) {
			output += "!"+key+" ";
		}
		return output;
	}
	
	private void joinChannel() {
		commands.put("join", (event, args) -> {
			IVoiceChannel ivc = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();
			if (ivc == null) return;
			ivc.join();
		});
	}
	
	private void leaveChannel() {
		commands.put("leave", (event, args) ->{
			IVoiceChannel ivc = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild()).getChannel();
			if (ivc == null) return;
			ivc.leave();
		});
	}
	// not working
	private void moveUser() {
		commands.put("move", (event, args) ->{
			String[] arg = args.get(0).split(" ");
			if (!(arg.length == 2)) return;
			System.out.println(arg.length);
			
			List<IVoiceChannel> channels = event.getGuild().getVoiceChannels();
			List<IUser> allUsers = event.getGuild().getUsers();
			
			for (int i = 0; i < channels.size(); i++)System.out.println(channels.get(i).getName());
			for (int i = 0; i < allUsers.size(); i++) System.out.println(allUsers.get(i).getName());
			for (int i = 0; i < channels.size(); i++) {
				if (channels.get(i).getName().equals(arg[1])) {
					if (arg[0].equalsIgnoreCase("all")) {
						System.out.println(allUsers.size());
						for (int j = 0; j < allUsers.size(); j++) {
							User u = new User(allUsers.get(j).getShard(),
											  allUsers.get(j).getClient(),
											  allUsers.get(j).getName(),
											  allUsers.get(j).getLongID(),
											  allUsers.get(j).getDiscriminator(),
											  allUsers.get(j).getAvatar(),
											  allUsers.get(j).getPresence(),
											  false);
							u.moveToVoiceChannel(channels.get(i));
						}
					} else {
						if (allUsers.get(i).getName().equals(arg[0])) {
							User u = new User(allUsers.get(i).getShard(),
									  allUsers.get(i).getClient(),
									  allUsers.get(i).getName(),
									  allUsers.get(i).getLongID(),
									  allUsers.get(i).getDiscriminator(),
									  allUsers.get(i).getAvatar(),
									  allUsers.get(i).getPresence(),
									  false);
							u.moveToVoiceChannel(channels.get(i));
						}
					}
				}
			}
		});
	}
}
