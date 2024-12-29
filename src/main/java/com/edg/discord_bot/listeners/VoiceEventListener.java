package com.edg.discord_bot.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edg.discord_bot.handlers.SelfDeafenHandler;

import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceSelfDeafenEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Component
@NoArgsConstructor
public class VoiceEventListener extends ListenerAdapter {

	@Autowired
	private SelfDeafenHandler selfDeafenHandler;

	@Override
	public void onGuildVoiceSelfDeafen(@Nonnull GuildVoiceSelfDeafenEvent event) {
		selfDeafenHandler.handleSelfDeafen(event);
	}

}
