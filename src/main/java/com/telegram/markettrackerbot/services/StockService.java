package com.telegram.markettrackerbot.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
  public InlineKeyboardMarkup getStockKeyboardMarkup(JSONObject data) {
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
}
