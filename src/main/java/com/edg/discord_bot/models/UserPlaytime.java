package com.edg.discord_bot.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPlaytime {

	private String currentGame;
	private long totalPlaytimeInSeconds;
	private long startTime;

	public void addPlaytime(long additionalPlaytimeInSeconds) {
		this.totalPlaytimeInSeconds += additionalPlaytimeInSeconds;
	}

	public void resetPlaytime() {
		this.totalPlaytimeInSeconds = 0;
	}

}
