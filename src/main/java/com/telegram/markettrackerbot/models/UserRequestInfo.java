package com.telegram.markettrackerbot.models;

public class UserRequestInfo {
  private final Long userId;
  private final Long chatId;
  private final String userName;
  private final String text;

  public UserRequestInfo(Long userId, Long chatId, String userName, String text) {
    this.userId = userId;
    this.chatId = chatId;
    this.userName = userName;
    this.text = text;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getChatId() {
    return chatId;
  }

  public String getUserName() {
    return userName;
  }

  public String getText() {
    return text;
  }
}
