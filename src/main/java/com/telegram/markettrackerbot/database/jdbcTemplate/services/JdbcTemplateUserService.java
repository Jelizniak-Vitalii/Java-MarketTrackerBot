package com.telegram.markettrackerbot.database.jdbcTemplate.services;

import com.telegram.markettrackerbot.database.jdbcTemplate.dao.JdbcTemplateUserDao;
import com.telegram.markettrackerbot.database.jdbcTemplate.models.JdbcTemplateUserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.telegram.markettrackerbot.database.AbstractDataBaseService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JdbcTemplateUserService extends AbstractDataBaseService {
  private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateUserService.class);

  JdbcTemplateUserDao jdbcTemplateUserDao;

  public JdbcTemplateUserService(JdbcTemplateUserDao jdbcTemplateUserDao) {
    this.jdbcTemplateUserDao = jdbcTemplateUserDao;
  }

  @Override
  public void createUser(Long chatId, String name) {
    try {
      JdbcTemplateUserModel user = jdbcTemplateUserDao.getUserByChatId(chatId);

      if (user == null) {
        jdbcTemplateUserDao.createUser(chatId, name);
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  @Override
  public void addStock(Long chatId, String stockId) {
    try {
      String stock = jdbcTemplateUserDao.getStockByTickerId(stockId);

      if (stock == null) {
        jdbcTemplateUserDao.addStock(chatId, stockId);
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  @Override
  public String[] getStocks(Long chatId) {
    try {
      return jdbcTemplateUserDao.getStocksByChatId(chatId);
    } catch (IOException e) {
      logger.error(e.getMessage());
      return null;
    }
  }
}
