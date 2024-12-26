package com.telegram.markettrackerbot.database.pureSql.models;

import java.sql.Timestamp;
import java.util.List;

public class UserModel {
  private String name;
  private String username;
  private Long chatId;
  private Timestamp createdAt;
  private List<String> stocks;

  public UserModel(Long chatId, String name, List<String> stocks) {
    this.chatId = chatId;
    this.name = name;
    this.stocks = stocks;
  }

  public UserModel(Long chatId, String name) {
    this(chatId, name, List.of());  // По умолчанию пустой список
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Long getChatId() {
    return chatId;
  }

  public void setChatId(Long chatId) {
    this.chatId = chatId;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
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
      ", username='" + username + '\'' +
      ", chatId=" + chatId +
      ", createdAt=" + createdAt +
      ", stocks=" + stocks +
      '}';
  }
}
