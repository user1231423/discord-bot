package com.edg.discord_bot.cache;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.edg.discord_bot.models.UserPlaytime;
import com.github.benmanes.caffeine.cache.Cache;

@Component
public class PlayTimeCache {
	private final Cache<String, UserPlaytime> userPlaytimeCache;

	public PlayTimeCache(Cache<String, UserPlaytime> userPlaytimeCache) {
		this.userPlaytimeCache = userPlaytimeCache;
	}

	public Map<String, UserPlaytime> getAllPlayTimes() {
		return userPlaytimeCache.asMap();
	}

	public UserPlaytime getUserPlaytime(String username) {
		return userPlaytimeCache.getIfPresent(username);
	}

	public void putUserPlaytime(String username, UserPlaytime userPlaytime) {
		userPlaytimeCache.put(username, userPlaytime);
	}

	public void clearUserPlaytime(String username) {
		userPlaytimeCache.invalidate(username);
	}

	public void clearAllPlaytimes() {
		userPlaytimeCache.invalidateAll();
	}

	public long getCacheSize() {
		return userPlaytimeCache.estimatedSize();
	}

}
