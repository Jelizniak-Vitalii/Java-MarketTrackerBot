package com.telegram.markettrackerbot.handlers;

import org.springframework.stereotype.Component;

import com.telegram.markettrackerbot.helpers.KeyboardHelper;
import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.models.UserRequest;
import static com.telegram.markettrackerbot.constants.Commands.START;
import static com.telegram.markettrackerbot.constants.Messages.WELCOME;

@Component
public class StartHandler extends UserRequestHandler {
	private final KeyboardHelper keyboardHelper;

	public StartHandler(KeyboardHelper keyboardHelper) {
		this.keyboardHelper = keyboardHelper;
	}

	@Override
	public boolean isApplicable(UserRequest request) {
		return isCommand(request.getUpdate(), START);
	}

	@Override
	public MessageResponse handle(UserRequest request) {
		return new MessageResponse(WELCOME, keyboardHelper.buildMainMenu());
	}
}
