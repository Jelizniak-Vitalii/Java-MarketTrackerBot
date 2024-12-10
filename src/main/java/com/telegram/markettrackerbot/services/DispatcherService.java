package com.telegram.markettrackerbot.services;

import java.util.List;

import com.telegram.markettrackerbot.helpers.KeyboardHelper;
import org.springframework.stereotype.Service;

import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.handlers.UserRequestHandler;
import com.telegram.markettrackerbot.models.UserRequest;
import static com.telegram.markettrackerbot.constants.Messages.NOT_FOUND;

@Service
public class DispatcherService {
	private final KeyboardHelper keyboardHelper;

	private final List<UserRequestHandler> handlers;

	public DispatcherService(List<UserRequestHandler> handlers, KeyboardHelper keyboardHelper) {
		this.handlers = handlers;
		this.keyboardHelper = keyboardHelper;
	}

	public MessageResponse dispatch(UserRequest userRequest) {
		for (UserRequestHandler userRequestHandler : handlers) {
			if (userRequestHandler.isApplicable(userRequest)) {
				return userRequestHandler.handle(userRequest);
			}
		}

		return new MessageResponse(NOT_FOUND, keyboardHelper.buildMainMenu());
	}
}