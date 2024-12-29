package com.edg.discord_bot.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.edg.discord_bot.handlers.JoinVoiceHandler;

import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Component
@NoArgsConstructor
public class JoinVoiceListener extends ListenerAdapter {

	@Autowired
	@Lazy
	private JoinVoiceHandler memberHandler;

	@Override
	public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
		memberHandler.handleMemberIsNowOnline(event);
	}

}
