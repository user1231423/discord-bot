package com.edg.discord_bot.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@Component
public class JDACaller {
	private static final Logger LOGGER = LoggerFactory.getLogger(JDACaller.class);

	private final JDA jda;

	public JDACaller(JDA jda) {
		this.jda = jda;
	}

	@PostConstruct
	public void init() {
		// This will run after the JDA bean is injected
	}

	public void sendMessageToDiscord(String aMessage, String aChannelId) {
		TextChannel channel = jda.getTextChannelById(aChannelId);

		if (channel != null) {
			channel.sendMessage(aMessage).queue();
			LOGGER.info("Message sent to Discord: " + aMessage);
		} else {
			LOGGER.error("Channel with ID " + aChannelId + " not found.");
		}
	}

}
