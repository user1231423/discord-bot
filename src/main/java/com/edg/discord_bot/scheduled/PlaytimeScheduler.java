package com.edg.discord_bot.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edg.discord_bot.cache.PlayTimeCache;
import com.edg.discord_bot.external.JDACaller;

@Component
@EnableScheduling
public class PlaytimeScheduler {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlaytimeScheduler.class);

	@Autowired
	private PlayTimeCache playtimeCache;

	@Autowired
	private JDACaller jdaCaller;

	private final String channelId = "732690069713846384";

	@Scheduled(cron = "0 0 0 * * ?")
	public void schedulePlaytimeReport() {
		LOGGER.info("Starting scheduled job..");

		playtimeCache.getAllPlayTimes().forEach((username, userPlaytime) -> {
			if (userPlaytime != null) {
				long totalPlaytime = userPlaytime.getTotalPlaytimeInSeconds();

				String message = username + " jogou hoje " + totalPlaytime + " seconds";

				jdaCaller.sendMessageToDiscord(message, channelId);

				userPlaytime.resetPlaytime();
				playtimeCache.putUserPlaytime(username, userPlaytime);
			}
		});

		LOGGER.info("Finished scheduled job, clearing all play time cached items...");
		playtimeCache.clearAllPlaytimes();
	}
}
