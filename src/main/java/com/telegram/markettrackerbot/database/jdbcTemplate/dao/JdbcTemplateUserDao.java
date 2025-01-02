package com.telegram.markettrackerbot.database.jdbcTemplate.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.telegram.markettrackerbot.database.jdbcTemplate.models.JdbcTemplateUserModel;

import java.io.IOException;
import java.sql.Array;

@Repository
public class JdbcTemplateUserDao {
  JdbcTemplate jdbcTemplate;

  public JdbcTemplateUserDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public JdbcTemplateUserModel getUserByChatId(Long chatId) throws IOException {
    String sql = "SELECT * FROM users WHERE chat_id = ?";

    try {
      return jdbcTemplate.query(
        sql,
        new Object[]{chatId},
        (rs, rowNum) -> new JdbcTemplateUserModel(
          rs.getLong("chat_id"),
          rs.getString("name")
        )
      )
        .stream()
        .findFirst()
        .orElse(null);
    } catch (DataAccessException e) {
      throw new IOException("Error while getting user by chat id: " + chatId, e);
    }
  }

  public String getStockByTickerId(String tickerId) throws IOException {
    String sql = "SELECT * FROM users WHERE ? = ANY(stocks)";

    try {
      Boolean result = jdbcTemplate.queryForObject(
        sql,
        new Object[]{tickerId},
        Boolean.class
      );

      return Boolean.TRUE.equals(result) ? tickerId : null;
    } catch (EmptyResultDataAccessException e) {
      return null;
    } catch (DataAccessException e) {
      throw new IOException("Error while getting stock by ticker id: " + tickerId, e);
    }
  }

  public String[] getStocksByChatId(Long chatId) throws IOException {
    String sql = "SELECT stocks FROM users WHERE chat_id = ?";

    try {
      return jdbcTemplate.query(
        sql,
        new Object[]{chatId},
        (rs, rowNum) -> {
          Array stocksArray = rs.getArray("stocks");
          return (String[]) stocksArray.getArray();
        }
      )
        .stream()
        .findFirst()
        .orElse(new String[0]);
    } catch (DataAccessException e) {
      throw new IOException("Error while getting user stocks by chat id: " + chatId, e);
    }
  }

  public void createUser(Long chatId, String name) throws IOException{
    String sql = "INSERT INTO users (name, chat_id, stocks) VALUES (?, ?, ?)";
    try {
      jdbcTemplate.update(sql, name, chatId, new String[0]);
    } catch (DataAccessException e) {
      throw new IOException("Error while creating user: " + chatId, e);
    }
  }

  public void addStock(Long chatId, String stockId) throws IOException {
    String sql = "UPDATE users SET stocks = array_append(stocks, ?) WHERE chat_id = ?";

    try {
      jdbcTemplate.update(sql, stockId, chatId);
    } catch (DataAccessException e) {
      throw new IOException("Error while updating user stocks: " + chatId, e);
    }
  }
}
