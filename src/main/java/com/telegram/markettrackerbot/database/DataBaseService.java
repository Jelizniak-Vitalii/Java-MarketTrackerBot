package com.telegram.markettrackerbot.database;

import com.telegram.markettrackerbot.database.jdbcTemplate.services.JdbcTemplateUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.telegram.markettrackerbot.database.jdbc.services.JdbcUserService;

@Service
public class DataBaseService extends AbstractDataBaseService {
  @Value("${db.type}")
  private String dataBaseType;

  private final JdbcUserService jdbcUserService;
  private final JdbcTemplateUserService jdbcTemplateUserService;

  public DataBaseService(
    JdbcUserService jdbcUserService,
    JdbcTemplateUserService jdbcTemplateUserService
  ) {
    this.jdbcUserService = jdbcUserService;
    this.jdbcTemplateUserService = jdbcTemplateUserService;
  }

  private AbstractDataBaseService getDataBaseService() {
    if (dataBaseType.equals("jdbc")) {
      return jdbcUserService;
    } else if (dataBaseType.equals("jdbcTemplate")) {
      return jdbcTemplateUserService;
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
