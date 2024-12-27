package com.telegram.markettrackerbot.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.telegram.markettrackerbot.database.jdbc.services.JdbcUserService;

@Service
public class DataBaseService {
  @Value("${db.type}")
  private String dataBaseType;

  private final JdbcUserService jdbcUserService;

  public DataBaseService(JdbcUserService jdbcUserService) {
    this.jdbcUserService = jdbcUserService;
  }

  private AbstractDataBaseService getDataBaseService() {
    if (dataBaseType.equals("jdbc")) {
      return jdbcUserService;
    }

    return jdbcUserService;
  }

  public void createUser(Long chatId, String name) {
    getDataBaseService().createUser(chatId, name);
  }

  public void addStock(Long chatId, String stockId) {
    getDataBaseService().addStock(chatId, stockId);
  }

  public String[] getStocks(Long chatId) {
    return getDataBaseService().getStocks(chatId);
  }
}
