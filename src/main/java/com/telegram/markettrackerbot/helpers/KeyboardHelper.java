package com.telegram.markettrackerbot.helpers;

import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import static com.telegram.markettrackerbot.constants.Buttons.FIND_STOCK;
import static com.telegram.markettrackerbot.constants.Buttons.WATCH_LIST;

@Component
public class KeyboardHelper {
	public ReplyKeyboardMarkup buildMainMenu() {
		List<KeyboardButton> buttons = List.of(
			new KeyboardButton(FIND_STOCK),
			new KeyboardButton(WATCH_LIST));

		return ReplyKeyboardMarkup.builder()
			.keyboard(List.of(new KeyboardRow(buttons)))
			.selective(true)
			.resizeKeyboard(true)
			.oneTimeKeyboard(false)
			.build();
	}
}
