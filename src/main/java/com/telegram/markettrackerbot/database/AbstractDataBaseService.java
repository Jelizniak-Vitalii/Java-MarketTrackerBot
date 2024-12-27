package com.telegram.markettrackerbot.database;

public abstract class AbstractDataBaseService {
  public abstract void createUser(Long chatId, String name);
  public abstract void addStock(Long chatId, String stockId);
  public abstract String[] getStocks(Long chatId);
}
