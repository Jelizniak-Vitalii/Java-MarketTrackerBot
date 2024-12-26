package com.telegram.markettrackerbot.services;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.handlers.UserRequestHandler;
import com.telegram.markettrackerbot.models.UserRequest;
import com.telegram.markettrackerbot.helpers.KeyboardHelper;
import com.telegram.markettrackerbot.models.UserRequestInfo;

import static com.telegram.markettrackerbot.constants.Messages.NOT_FOUND;

@Service
public class DispatcherService {
  private static final Logger logger = LoggerFactory.getLogger(DispatcherService.class);

  private final KeyboardHelper keyboardHelper;

	private final List<UserRequestHandler> handlers;

	public DispatcherService(List<UserRequestHandler> handlers, KeyboardHelper keyboardHelper) {
		this.handlers = handlers;
		this.keyboardHelper = keyboardHelper;
	}

	public MessageResponse dispatch(UserRequest userRequest) throws IOException {
		for (UserRequestHandler userRequestHandler : handlers) {
			if (userRequestHandler.isApplicable(userRequest)) {
				return userRequestHandler.handle(userRequest);
			}
		}

		return new MessageResponse(NOT_FOUND, keyboardHelper.buildMainMenu());
	}

  public UserRequestInfo getUserRequestInfo(Update update) {
    try {
      if (update.hasCallbackQuery()) {
        JSONObject jsonObject = new JSONObject(update.getCallbackQuery().getData());

        return new UserRequestInfo(
          update.getCallbackQuery().getFrom().getId(),
          update.getCallbackQuery().getMessage().getChatId(),
          update.getCallbackQuery().getFrom().getUserName(),
          update.getCallbackQuery().getData(),
          jsonObject.optString("action"),
          jsonObject.optString("tickerId")
        );
      } else if (update.hasMessage()) {
        return new UserRequestInfo(
          update.getMessage().getFrom().getId(),
          update.getMessage().getChatId(),
          update.getMessage().getFrom().getUserName(),
          update.getMessage().getText()
        );
      }
    } catch (Exception e) {
      logger.error("Failed to extract update info: ", e);
      return null;
    }

    return null;
  }
}
