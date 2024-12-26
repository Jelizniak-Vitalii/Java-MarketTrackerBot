package com.telegram.markettrackerbot.handlers;

import org.springframework.stereotype.Component;
import com.telegram.markettrackerbot.database.pureSql.services.UserService;
import com.telegram.markettrackerbot.helpers.KeyboardHelper;
import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.models.UserRequest;

import static com.telegram.markettrackerbot.constants.Commands.START;
import static com.telegram.markettrackerbot.constants.Messages.WELCOME;

@Component
public class StartHandler extends UserRequestHandler {
  private final KeyboardHelper keyboardHelper;
  private final UserService userService;

	public StartHandler(KeyboardHelper keyboardHelper, UserService userService) {
		this.keyboardHelper = keyboardHelper;
    this.userService = userService;
	}

	@Override
	public boolean isApplicable(UserRequest request) {
		return isCommand(request.getUpdate(), START);
	}

	@Override
	public MessageResponse handle(UserRequest request) {
    userService.createUser(request.getChatId(), request.getUserSession().getUserRequestInfo().getUserName());

    return new MessageResponse(WELCOME, keyboardHelper.buildMainMenu());
	}
}
