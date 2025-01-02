package com.telegram.markettrackerbot.database.jdbcTemplate.models;

import java.util.List;

public class JdbcTemplateUserModel {
  private String name;
  private Long chatId;
  private List<String> stocks;

  public JdbcTemplateUserModel() {}

  public JdbcTemplateUserModel(Long chatId, String name, List<String> stocks) {
    this.chatId = chatId;
    this.name = name;
    this.stocks = stocks;
  }

  public JdbcTemplateUserModel(Long chatId, String name) {
    this(chatId, name, List.of());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getChatId() {
    return chatId;
  }

  public void setChatId(Long chatId) {
    this.chatId = chatId;
  }

  public List<String> getStocks() {
    return stocks;
  }

  public void setStocks(List<String> stocks) {
    this.stocks = stocks;
  }

  @Override
  public String toString() {
    return "UserModel{" +
      ", name='" + name + '\'' +
      ", chatId=" + chatId +
      ", stocks=" + stocks +
      '}';
  }
}
