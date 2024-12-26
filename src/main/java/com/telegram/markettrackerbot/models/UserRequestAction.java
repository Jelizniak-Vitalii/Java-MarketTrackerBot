package com.telegram.markettrackerbot.models;

public class UserRequestAction {
  private final String action;
  private final String tickerId;

  public UserRequestAction(String action, String tickerId) {
    this.action = action;
    this.tickerId = tickerId;
  }

  public String getAction() {
    return action;
  }

  public String getTickerId() {
    return tickerId;
  }
}
