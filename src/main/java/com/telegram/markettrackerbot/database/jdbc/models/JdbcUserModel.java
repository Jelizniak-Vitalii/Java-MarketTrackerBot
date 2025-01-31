package com.telegram.markettrackerbot.database.jdbc.models;

import java.util.List;

public class JdbcUserModel {
  private String name;
  private Long chatId;
  private List<String> stocks;

  public JdbcUserModel(Long chatId, String name, List<String> stocks) {
    this.chatId = chatId;
    this.name = name;
    this.stocks = stocks;
  }

  public JdbcUserModel(Long chatId, String name) {
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
