package com.edg.discord_bot.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edg.discord_bot.handlers.ActivityHandler;

import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Component
@NoArgsConstructor
public class ActivityListener extends ListenerAdapter {

	@Autowired
	private ActivityHandler activityHandler;

	@Override
	public void onUserActivityStart(@Nonnull UserActivityStartEvent event) {
		activityHandler.handleActivityStart(event);
	}

	@Override
	public void onUserActivityEnd(@Nonnull UserActivityEndEvent event) {
		activityHandler.handleActivityEnd(event);
	}

}
