package com.telegram.markettrackerbot.handlers;

import org.springframework.stereotype.Component;
import com.telegram.markettrackerbot.database.pureSql.services.UserService;
import com.telegram.markettrackerbot.services.StockService;
import com.telegram.markettrackerbot.helpers.KeyboardHelper;
import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.models.UserRequest;

import static com.telegram.markettrackerbot.constants.Buttons.WATCH_LIST;

@Component
public class GetWatchListHandler extends UserRequestHandler {
  private final KeyboardHelper keyboardHelper;

  private final StockService stockService;
  private final UserService userService;

  public GetWatchListHandler(
    KeyboardHelper keyboardHelper,
    StockService stockService,
    UserService userService
  ) {
    this.keyboardHelper = keyboardHelper;
    this.stockService = stockService;
    this.userService = userService;
  }

  @Override
  public boolean isApplicable(UserRequest request) {
    return isCommand(request.getUpdate(), WATCH_LIST);
  }

  @Override
  public MessageResponse handle(UserRequest request) {
    String[] watchList = this.userService.getStocks(request.getChatId());
    String message = this.stockService.getWatchListMessage(watchList);

    return new MessageResponse(message, keyboardHelper.buildMainMenu());
  }
}
