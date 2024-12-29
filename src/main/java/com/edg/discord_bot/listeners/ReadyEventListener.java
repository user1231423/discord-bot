package com.edg.discord_bot.listeners;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.edg.discord_bot.config.JDAConfig;

import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Component
@NoArgsConstructor
public class ReadyEventListener extends ListenerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(JDAConfig.class);

	@Override
	public void onReady(@NotNull ReadyEvent event) {
		LOGGER.info("Bot is ready! Logged in as: " + event.getJDA().getSelfUser().getName());
	}

}
