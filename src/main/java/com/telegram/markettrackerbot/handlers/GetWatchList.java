package com.telegram.markettrackerbot.handlers;

import org.springframework.stereotype.Component;
import com.telegram.markettrackerbot.helpers.KeyboardHelper;
import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.models.UserRequest;
import com.telegram.markettrackerbot.models.UserSession;
import com.telegram.markettrackerbot.services.UserSessionService;

import static com.telegram.markettrackerbot.constants.Buttons.WATCH_LIST;
import static com.telegram.markettrackerbot.constants.Messages.WATCH_LIST_MESSAGE;

@Component
public class GetWatchList extends UserRequestHandler {
  private final KeyboardHelper keyboardHelper;

  private final UserSessionService userSessionService;

  public GetWatchList(KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
    this.keyboardHelper = keyboardHelper;
    this.userSessionService = userSessionService;
  }

  @Override
  public boolean isApplicable(UserRequest request) {
    return isCommand(request.getUpdate(), WATCH_LIST);
  }

  @Override
  public MessageResponse handle(UserRequest request) {
    UserSession userSession = request.getUserSession();

    return new MessageResponse(WATCH_LIST_MESSAGE, keyboardHelper.buildMainMenu());
  }
}
