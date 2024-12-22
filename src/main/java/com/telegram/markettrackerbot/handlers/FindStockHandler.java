package com.telegram.markettrackerbot.handlers;

import org.springframework.stereotype.Component;

import com.telegram.markettrackerbot.enums.ChatState;
import com.telegram.markettrackerbot.models.UserSession;
import com.telegram.markettrackerbot.services.UserSessionService;
import com.telegram.markettrackerbot.helpers.KeyboardHelper;
import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.models.UserRequest;

import static com.telegram.markettrackerbot.constants.Buttons.FIND_STOCK;
import static com.telegram.markettrackerbot.constants.Messages.FIND_STOCK_MESSAGE;

@Component
public class FindStockHandler extends UserRequestHandler {
  private final KeyboardHelper keyboardHelper;

  private final UserSessionService userSessionService;

  public FindStockHandler(KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
    this.keyboardHelper = keyboardHelper;
    this.userSessionService = userSessionService;
  }

  @Override
  public boolean isApplicable(UserRequest request) {
    return isCommand(request.getUpdate(), FIND_STOCK);
  }

  @Override
  public MessageResponse handle(UserRequest request) {
    UserSession userSession = request.getUserSession();
    userSession.setState(ChatState.FIND_STOCK);
    userSessionService.saveSession(request.getChatId(), userSession);

    return new MessageResponse(FIND_STOCK_MESSAGE, keyboardHelper.buildMainMenu());
  }
}
