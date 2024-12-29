package com.edg.discord_bot.listeners;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Component
@NoArgsConstructor
public class MessageEmbedListener extends ListenerAdapter {
	private static final String[] FORBIDDEN_IMAGES = {
			"b84a5a2adfdc1b835ef2180e4f9ac08925af712a46535853e1088a19a9956388",
			"a30c4980ae483ba5fd98e51fca6b8e1b4712e02c5978ef330a510fa1057edcab" };

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		for (Attachment attachment : event.getMessage().getAttachments()) {
			String imageUrl = attachment.getProxyUrl();
			String fileName = attachment.getFileName().toLowerCase();

			if (fileName.contains("verstappen")) {
				event.getMessage().delete().queue();
				return;
			}

			try (InputStream in = new URI(imageUrl).toURL().openStream()) {
				Path tempFile = Files.createTempFile("discord-upload", ".tmp");

				Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);

				String hash = DigestUtils.sha256Hex(Files.newInputStream(tempFile));

				for (String forbiddenHash : FORBIDDEN_IMAGES) {
					if (hash.equalsIgnoreCase(forbiddenHash)) {
						event.getMessage().delete().queue();
						break;
					}
				}

				Files.deleteIfExists(tempFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
