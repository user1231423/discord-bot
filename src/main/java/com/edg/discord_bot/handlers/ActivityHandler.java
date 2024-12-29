package com.edg.discord_bot.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edg.discord_bot.cache.PlayTimeCache;
import com.edg.discord_bot.config.JDAConfig;
import com.edg.discord_bot.models.UserPlaytime;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;

@Service
public class ActivityHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(JDAConfig.class);
	private static final String SPOTIFY = "Spotify";

	@Autowired
	private PlayTimeCache playtimeCache;

	public void handleActivityStart(@Nonnull UserActivityStartEvent event) {
		String username = event.getUser().getName();
		String gameName = event.getNewActivity().getName();

		if (SPOTIFY.equalsIgnoreCase(gameName)) {
			return;
		}

		UserPlaytime userPlaytime = playtimeCache.getUserPlaytime(username);

		if (userPlaytime != null && gameName.equals(userPlaytime.getCurrentGame())) {
			LOGGER.info(username + " is already playing " + gameName + ". Not starting a new session.");
			return;
		}

		if (userPlaytime == null) {
			userPlaytime = new UserPlaytime();
		}

		userPlaytime.setStartTime(System.currentTimeMillis());
		userPlaytime.setCurrentGame(gameName);

		playtimeCache.putUserPlaytime(username, userPlaytime);

		LOGGER.info(username + " started playing " + gameName);
	}

	public void handleActivityEnd(@Nonnull UserActivityEndEvent event) {
		String username = event.getUser().getName();
		String gameName = event.getOldActivity().getName();

		if (SPOTIFY.equalsIgnoreCase(gameName)) {
			return;
		}

		UserPlaytime userPlaytime = playtimeCache.getUserPlaytime(username);

		if (userPlaytime != null && gameName.equals(userPlaytime.getCurrentGame())) {
			long playTimeInSeconds = (System.currentTimeMillis() - userPlaytime.getStartTime()) / 1000;
			userPlaytime.addPlaytime(playTimeInSeconds);

			playtimeCache.putUserPlaytime(username, userPlaytime);

			LOGGER.info(username + " stopped playing " + gameName + ". Total playtime: "
					+ userPlaytime.getTotalPlaytimeInSeconds() + " seconds");
		}
	}

}
