package com.telegram.markettrackerbot.handlers;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.telegram.markettrackerbot.services.StockHttpService;
import com.telegram.markettrackerbot.enums.ChatState;
import com.telegram.markettrackerbot.helpers.KeyboardHelper;
import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.models.UserRequest;
import com.telegram.markettrackerbot.services.StockService;

import static com.telegram.markettrackerbot.constants.Buttons.FIND_STOCK;
import static com.telegram.markettrackerbot.constants.Buttons.WATCH_LIST;
import static com.telegram.markettrackerbot.constants.Commands.START;
import static com.telegram.markettrackerbot.constants.Messages.STOCK_FOUND;
import static com.telegram.markettrackerbot.constants.Messages.STOCK_NOT_FOUND;

@Component
public class EnteredFindStockHandler extends UserRequestHandler {
  private static final Logger logger = LoggerFactory.getLogger(EnteredFindStockHandler.class);

  private final KeyboardHelper keyboardHelper;
  private final StockService stockService;
  private final StockHttpService stockHttpService;

  public EnteredFindStockHandler(
    KeyboardHelper keyboardHelper,
    StockService stockService,
    StockHttpService stockHttpService
  ) {
    this.keyboardHelper = keyboardHelper;
    this.stockService = stockService;
    this.stockHttpService = stockHttpService;
  }

  @Override
  public boolean isApplicable(UserRequest request) {
    return isTextMessage(request.getUpdate()) &&
      ChatState.FIND_STOCK.equals(request.getUserSession().getState()) &&
      !isCommand(request.getUpdate(), WATCH_LIST) &&
      !isCommand(request.getUpdate(), FIND_STOCK) &&
      !isCommand(request.getUpdate(), START);
  }

  @Override
  public MessageResponse handle(UserRequest request) {
    try {
      String response = this.stockHttpService.search(request.getUpdate().getMessage().getText());
      JSONObject jsonObject = new JSONObject(response);
      boolean isStockFound = jsonObject.has("stocks");

      return new MessageResponse(
        isStockFound ? STOCK_FOUND : STOCK_NOT_FOUND,
        keyboardHelper.buildMainMenu(),
        isStockFound ? stockService.getStockKeyboardMarkup(jsonObject) : null
      );
    } catch (Exception e) {
      logger.error(e.getMessage());

      return new MessageResponse(
        STOCK_NOT_FOUND,
        keyboardHelper.buildMainMenu()
      );
    }
  }
}
