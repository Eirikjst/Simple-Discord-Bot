package com.eirikjst.musicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {

	public final AudioPlayer player;
	public final TrackScheduler scheduler;

	public GuildMusicManager(AudioPlayerManager manager) {
		player = manager.createPlayer();
		scheduler = new TrackScheduler(player);
		player.addListener(scheduler);
	}

	public AudioProvider getAudioProvider() {
		return new AudioProvider(player);
	}
}