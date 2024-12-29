package com.edg.discord_bot.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.edg.discord_bot.config.JDAConfig;

import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceSelfDeafenEvent;

@Service
@NoArgsConstructor
public class SelfDeafenHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(JDAConfig.class);

	public void handleSelfDeafen(@Nonnull GuildVoiceSelfDeafenEvent event) {
		Member member = event.getMember();

		if (member != null) {
			if (event.isSelfDeafened()) {
				VoiceChannel afkChannel = event.getGuild().getAfkChannel();

				if (afkChannel != null) {
					// Move the user to the AFK channel if the channel exists
					afkChannel.getGuild().moveVoiceMember(member, afkChannel).queue(
							_ -> LOGGER.info("Moved user to the AFK channel." + member.getEffectiveName()),
							failure -> LOGGER.error("Failed to move user to AFK channel: " + member.getEffectiveName(),
									failure.getMessage()));
				} else {
					LOGGER.warn("AFK channel not found: " + event.getGuild().getName());
				}
			}
		}
	}

}
