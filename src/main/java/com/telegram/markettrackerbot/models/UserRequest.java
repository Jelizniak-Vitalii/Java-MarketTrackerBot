package com.telegram.markettrackerbot.models;

import org.telegram.telegrambots.meta.api.objects.Update;

public class UserRequest {
	private Update update;
	private Long chatId;
	private UserSession userSession;

	public UserRequest() {}

	public UserRequest(Update update, Long chatId, UserSession userSession) {
		this.update = update;
		this.chatId = chatId;
		this.userSession = userSession;
	}

	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public static class Builder {
		private Update update;
		private Long chatId;
		private UserSession userSession;

		public Builder update(Update update) {
			this.update = update;
			return this;
		}

		public Builder chatId(Long chatId) {
			this.chatId = chatId;
			return this;
		}

		public Builder userSession(UserSession userSession) {
			this.userSession = userSession;
			return this;
		}

		public UserRequest build() {
			return new UserRequest(update, chatId, userSession);
		}
	}

	public static Builder builder() {
		return new Builder();
	}
}
