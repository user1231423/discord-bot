package com.edg.discord_bot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edg.discord_bot.external.JDACaller;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;

@Service
public class JoinVoiceHandler {
	private final String[] links = { "https://www.net-empregos.com/emprego-madeira.asp", "https://www.indeed.com",
			"https://europass.europa.eu/en" };

	@Autowired
	private JDACaller jdaCaller;

	private final String nocxirChannelId = "1308448127321247774";
	private final String targetMemberName = "nocxir";

	public void handleMemberIsNowOnline(@Nonnull GuildVoiceUpdateEvent event) {
		if (event.getChannelJoined() != null) {
			String username = event.getMember().getUser().getName();

			if (username.equalsIgnoreCase(targetMemberName)) {
				String userId = event.getMember().getId();
				StringBuilder messageBuilder = new StringBuilder();

				messageBuilder.append("Bom dia <@!").append(userId)
						.append("> tens aqui uma ajuda pra encontrar trabalho!\n\n");
				messageBuilder.append("Podes começar a ver estes links e a mandar curriculos:\n");
				for (String link : links) {
					messageBuilder.append(link).append("\n");
				}

				jdaCaller.sendMessageToDiscord(messageBuilder.toString(), nocxirChannelId);
			}
		}
	}

}
