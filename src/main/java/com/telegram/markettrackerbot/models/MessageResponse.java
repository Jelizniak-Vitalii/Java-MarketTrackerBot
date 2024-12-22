package com.telegram.markettrackerbot.models;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class MessageResponse {
	private final String text;
	private final ReplyKeyboardMarkup keyboard;
	private final InlineKeyboardMarkup inlineKeyboard;

  public MessageResponse(String text) {
    this.text = text;
    this.keyboard = null;
    this.inlineKeyboard = null;
  }

	public MessageResponse(String text, ReplyKeyboardMarkup keyboard) {
		this.text = text;
		this.keyboard = keyboard;
    this.inlineKeyboard = null;
	}

  public MessageResponse(String text, ReplyKeyboardMarkup keyboard, InlineKeyboardMarkup inlineKeyboard) {
    this.text = text;
    this.keyboard = keyboard;
    this.inlineKeyboard = inlineKeyboard;
  }

	public String getText() {
		return text;
	}

	public ReplyKeyboardMarkup getKeyboard() {
		return keyboard;
	}

  public InlineKeyboardMarkup getInlineKeyboard() {
    return inlineKeyboard;
  }
}
