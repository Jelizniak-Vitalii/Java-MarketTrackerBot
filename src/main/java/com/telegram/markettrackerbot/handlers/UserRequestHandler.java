package com.telegram.markettrackerbot.handlers;

import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.models.UserRequest;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class UserRequestHandler {
	public abstract boolean isApplicable(UserRequest request);
	public abstract MessageResponse handle(UserRequest dispatchRequest);

	public boolean isCommand(Update update, String command) {
		System.out.println(update.getMessage().getText());
		return update.hasMessage() && update.getMessage().isCommand()
			&& update.getMessage().getText().equals(command);
	}

	public boolean isTextMessage(Update update) {
		return update.hasMessage() && update.getMessage().hasText();
	}

	public boolean isTextMessage(Update update, String text) {
		return update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals(text);
	}
}
