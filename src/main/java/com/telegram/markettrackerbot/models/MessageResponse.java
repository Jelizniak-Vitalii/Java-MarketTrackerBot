package com.telegram.markettrackerbot.models;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class MessageResponse {
	private final String text;
	private final ReplyKeyboardMarkup keyboard;

	public MessageResponse(String text, ReplyKeyboardMarkup keyboard) {
		this.text = text;
		this.keyboard = keyboard;
	}

	public MessageResponse(String text) {
		this.text = text;
		this.keyboard = null;
	}

	public String getText() {
		return text;
	}

	public ReplyKeyboardMarkup getKeyboard() {
		return keyboard;
	}
}