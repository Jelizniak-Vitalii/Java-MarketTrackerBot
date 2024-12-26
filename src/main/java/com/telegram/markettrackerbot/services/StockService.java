package com.telegram.markettrackerbot.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import static com.telegram.markettrackerbot.constants.Actions.ADD_TO_WATCH_LIST_ACTION;

@Service
public class StockService {
  private final StockHttpService stockHttpService;

  public StockService(StockHttpService stockHttpService) {
    this.stockHttpService = stockHttpService;
  }
  public InlineKeyboardMarkup getStocksKeyboardMarkup(JSONObject data) {
    JSONObject stocksObject = data.getJSONObject("stocks");
    JSONArray dataArray = stocksObject.getJSONArray("datas");

    List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

    for (int i = 0; i < dataArray.length(); i++) {
      JSONObject stockObject = dataArray.getJSONObject(i);
      JSONObject tickerObject = stockObject.getJSONObject("ticker");

      String name = tickerObject.getString("name");
      String symbol = tickerObject.getString("symbol");
      String tickerId = String.valueOf(tickerObject.getInt("tickerId"));

      InlineKeyboardButton button = new InlineKeyboardButton();
      button.setText(name + " - " + symbol);

      JSONObject callbackData = new JSONObject();
      callbackData.put("action", "findStock");
      callbackData.put("tickerId", tickerId);

      button.setCallbackData(callbackData.toString());

      keyboard.add(List.of(button));
    }

    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    markup.setKeyboard(keyboard);

    return markup;
  }

  public String getStockInfoMessage(String data) {
    JSONObject stockInfo = new JSONObject(data);

    return String.format(
      "%s - %s: цена %s %s",
      stockInfo.getString("name"),
      stockInfo.getString("symbol"),
      stockInfo.has("pPrice") ? stockInfo.getString("pPrice") : stockInfo.getString("close"),
      stockInfo.has("currencyCode") ? stockInfo.getString("currencyCode") : ""
    );
  }

  public String getWatchListMessage(String[] stocks) {
    StringBuilder message = new StringBuilder();

    for (String stock : stocks) {
      String stockData = stockHttpService.getStockByTickerId(stock);

      if (stockData != null && !stockData.isEmpty()) {
        message.append(getStockInfoMessage(stockData)).append("\n");
      }
    }

    return message.toString();
  }

  public InlineKeyboardMarkup getStockInfoKeyboardMarkup(String data) {
    JSONObject stockInfo = new JSONObject(data);

    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    InlineKeyboardButton addToWatchlistButton = new InlineKeyboardButton();
    addToWatchlistButton.setText("Добавить в WatchList");
    addToWatchlistButton.setCallbackData(String.format(
      "{\"action\": \"%1s\", \"tickerId\":%2d}",
      ADD_TO_WATCH_LIST_ACTION,
      stockInfo.getInt("tickerId")
    ));
    inlineKeyboardMarkup.setKeyboard(Collections.singletonList(Collections.singletonList(addToWatchlistButton)));

    return inlineKeyboardMarkup;
  }
}
