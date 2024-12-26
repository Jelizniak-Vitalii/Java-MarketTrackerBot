package com.telegram.markettrackerbot.database.pureSql.services;

import java.io.IOException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.telegram.markettrackerbot.database.pureSql.models.UserModel;
import com.telegram.markettrackerbot.database.pureSql.dao.UserModelDao;

@Service
public class UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final UserModelDao userModelDao;

  public UserService(UserModelDao userModelDao) {
    this.userModelDao = userModelDao;
  }

  public void createUser(Long chatId, String name) {
    try {
      UserModel user = userModelDao.getUserByChatId(chatId);

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
