package com.edg.discord_bot.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.edg.discord_bot.models.UserPlaytime;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	Cache<String, UserPlaytime> userPlaytimeCache() {
		return Caffeine.newBuilder().maximumSize(50).expireAfterWrite(24, TimeUnit.HOURS).build();
	}

}