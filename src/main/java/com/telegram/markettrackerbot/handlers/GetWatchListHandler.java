package com.telegram.markettrackerbot.handlers;

import org.springframework.stereotype.Component;
import com.telegram.markettrackerbot.services.StockService;
import com.telegram.markettrackerbot.helpers.KeyboardHelper;
import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.models.UserRequest;
import com.telegram.markettrackerbot.database.DataBaseService;

import static com.telegram.markettrackerbot.constants.Buttons.WATCH_LIST;
import static com.telegram.markettrackerbot.constants.Messages.WATCH_LIST_EMPTY_MESSAGE;

@Component
public class GetWatchListHandler extends UserRequestHandler {
  private final KeyboardHelper keyboardHelper;

  private final StockService stockService;
  private final DataBaseService dataBaseService;

  public GetWatchListHandler(
    KeyboardHelper keyboardHelper,
    StockService stockService,
    DataBaseService dataBaseService
  ) {
    this.keyboardHelper = keyboardHelper;
    this.stockService = stockService;
    this.dataBaseService = dataBaseService;
  }

  @Override
  public boolean isApplicable(UserRequest request) {
    return isCommand(request.getUpdate(), WATCH_LIST);
  }

  @Override
  public MessageResponse handle(UserRequest request) {
    String[] watchList = this.dataBaseService.getStocks(request.getChatId());
    String message = this.stockService.getWatchListMessage(watchList);

    return new MessageResponse(message.isEmpty() ? WATCH_LIST_EMPTY_MESSAGE : message, keyboardHelper.buildMainMenu());
  }
}
