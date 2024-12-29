package com.edg.discord_bot.config;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.edg.discord_bot.listeners.ActivityListener;
import com.edg.discord_bot.listeners.JoinVoiceListener;
import com.edg.discord_bot.listeners.MessageEmbedListener;
import com.edg.discord_bot.listeners.ReadyEventListener;
import com.edg.discord_bot.listeners.VoiceEventListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

@Configuration
public class JDAConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(JDAConfig.class);

	@Autowired
	private DiscordBotConfig discordBotConfig;

	@Autowired
	private ReadyEventListener readyEventListener;

	@Autowired
	private VoiceEventListener voiceEventListener;

	@Autowired
	private ActivityListener activityListener;

	@Autowired
	private JoinVoiceListener memberListener;

	@Autowired
	private MessageEmbedListener messageEmbedListener;

	@Bean
	JDA jda() throws LoginException {
		LOGGER.info("Initializing Discord bot...");

		JDABuilder builder = JDABuilder.createDefault(discordBotConfig.getToken())
				.enableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES,
						GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.MESSAGE_CONTENT)
				.setMemberCachePolicy(MemberCachePolicy.ALL).enableCache(CacheFlag.ACTIVITY)
				.setActivity(Activity.playing("Waiting for Salazar orders")).addEventListeners(readyEventListener,
						voiceEventListener, activityListener, memberListener, messageEmbedListener);

		// Build the JDA instance and return it as a Spring bean
		return builder.build();
	}
}