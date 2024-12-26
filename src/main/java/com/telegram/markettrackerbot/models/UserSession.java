package com.telegram.markettrackerbot.models;

import com.telegram.markettrackerbot.enums.ChatState;

public class UserSession {
	private ChatState state;
	private Long chatId;
	private String city;
	private String text;
  private UserRequestInfo userRequestInfo;

	public UserSession() {}

	public UserSession(Long chatId, String text, ChatState state, String city, UserRequestInfo userRequestInfo) {
		this.chatId = chatId;
		this.state = state;
		this.text = text;
		this.city = city;
    this.userRequestInfo = userRequestInfo;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public ChatState getState() {
		return state;
	}

	public void setState(ChatState state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

  public UserRequestInfo getUserRequestInfo() {
    return userRequestInfo;
  }

  public void setUserRequestInfo(UserRequestInfo userRequestInfo) {
    this.userRequestInfo = userRequestInfo;
  }

	public static class Builder {
		private ChatState state;
		private Long chatId;
		private String city;
		private String text;
    private UserRequestInfo userRequestInfo;

		public Builder state(ChatState state) {
			this.state = state;
			return this;
		}

		public Builder chatId(Long chatId) {
			this.chatId = chatId;
			return this;
		}

		public Builder city(String city) {
			this.city = city;
			return this;
		}

		public Builder text(String text) {
			this.text = text;
			return this;
		}

    public Builder userRequestInfo(UserRequestInfo userRequestInfo) {
      this.userRequestInfo = userRequestInfo;
      return this;
    }

		public UserSession build() {
			return new UserSession(chatId, text, state, city, userRequestInfo);
		}
	}

	public static Builder builder() {
		return new Builder();
	}
}
