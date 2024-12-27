package com.telegram.markettrackerbot.database.jdbc.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.telegram.markettrackerbot.database.AbstractDataBaseService;
import com.telegram.markettrackerbot.database.jdbc.models.JdbcUserModel;
import com.telegram.markettrackerbot.database.jdbc.dao.JdbcUserModelDao;

@Service
public class JdbcUserService extends AbstractDataBaseService {
  private static final Logger logger = LoggerFactory.getLogger(JdbcUserService.class);

  private final JdbcUserModelDao userModelDao;

  public JdbcUserService(JdbcUserModelDao userModelDao) {
    this.userModelDao = userModelDao;
  }

  public void createUser(Long chatId, String name) {
    try {
      JdbcUserModel user = userModelDao.getUserByChatId(chatId);

      if (user == null) {
        userModelDao.createUser(chatId, name);
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public void addStock(Long chatId, String stockId) {
    try {
      String stock = userModelDao.getStockByTickerId(stockId);

      if (stock == null) {
        userModelDao.addStock(chatId, stockId);
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public String[] getStocks(Long chatId) {
    try {
      return userModelDao.getStocksByChatId(chatId);
    } catch (IOException e) {
      logger.error(e.getMessage());
      return null;
    }
  }
}
