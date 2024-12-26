package com.telegram.markettrackerbot.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.telegram.markettrackerbot.helpers.KeyboardHelper;
import com.telegram.markettrackerbot.services.StockHttpService;
import com.telegram.markettrackerbot.services.StockService;
import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.models.UserRequest;

import static com.telegram.markettrackerbot.constants.Actions.FIND_STOCK_ACTION;
import static com.telegram.markettrackerbot.constants.Messages.STOCK_NOT_FOUND;

@Component
public class GetStockInfoHandler extends UserRequestHandler {
  private static final Logger logger = LoggerFactory.getLogger(GetStockInfoHandler.class);

  private final StockService stockService;
  private final StockHttpService stockHttpService;
  private final KeyboardHelper keyboardHelper;

  public GetStockInfoHandler(
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
    return request.getUserSession().getUserRequestInfo().getUserRequestAction() != null &&
      request.getUserSession().getUserRequestInfo().getUserRequestAction().getAction().equals(FIND_STOCK_ACTION);
  }

  @Override
  public MessageResponse handle(UserRequest request) {
    try {
      String response = this.stockHttpService.getStockByTickerId(request.getUserSession().getUserRequestInfo().getUserRequestAction().getTickerId());

      return new MessageResponse(
        this.stockService.getStockInfoMessage(response),
        keyboardHelper.buildMainMenu(),
        this.stockService.getStockInfoKeyboardMarkup(response)
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
