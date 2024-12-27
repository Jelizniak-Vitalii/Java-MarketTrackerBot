package com.telegram.markettrackerbot.handlers;

import org.springframework.stereotype.Component;
import com.telegram.markettrackerbot.database.DataBaseService;
import com.telegram.markettrackerbot.helpers.KeyboardHelper;
import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.models.UserRequest;

import static com.telegram.markettrackerbot.constants.Actions.ADD_TO_WATCH_LIST_ACTION;
import static com.telegram.markettrackerbot.constants.Messages.STOCK_ADDED_TO_WATCH_LIST;

@Component
public class AddToWatchListStockHandler extends UserRequestHandler {
  private final KeyboardHelper keyboardHelper;
  private final DataBaseService dataBaseService;

  public AddToWatchListStockHandler(KeyboardHelper keyboardHelper, DataBaseService dataBaseService) {
    this.keyboardHelper = keyboardHelper;
    this.dataBaseService = dataBaseService;
  }

  @Override
  public boolean isApplicable(UserRequest request) {
    return request.getUserSession().getUserRequestInfo().getUserRequestAction() != null &&
      request.getUserSession().getUserRequestInfo().getUserRequestAction().getAction().equals(ADD_TO_WATCH_LIST_ACTION);
  }

  @Override
  public MessageResponse handle(UserRequest request) {
    this.dataBaseService.addStock(request.getChatId(), request.getUserSession().getUserRequestInfo().getUserRequestAction().getTickerId());

    return new MessageResponse(STOCK_ADDED_TO_WATCH_LIST, keyboardHelper.buildMainMenu());
  }
}
