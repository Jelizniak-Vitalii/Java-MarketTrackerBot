package com.telegram.markettrackerbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.telegram.markettrackerbot.models.UserRequest;
import com.telegram.markettrackerbot.models.UserSession;
import com.telegram.markettrackerbot.services.UserSessionService;
import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.services.DispatcherService;

@Component
public class TelegramBot extends TelegramLongPollingBot {
	private static final Logger logger = LoggerFactory.getLogger(com.telegram.markettrackerbot.bot.TelegramBot.class);
	private final UserSessionService userSessionService;
	private final DispatcherService dispatcherService;

	@Value("${bot.token}")
	private String botToken;

	@Value("${bot.username}")
	private String botUsername;

	@Autowired
	public TelegramBot(
			UserSessionService userSessionService,
			DispatcherService dispatcherService
	) {
		this.userSessionService = userSessionService;
		this.dispatcherService = dispatcherService;
	}

	@Override
	public String getBotUsername() {
		return botUsername;
	}

	@Override
	public String getBotToken() {
		return botToken;
	}

	@Override
	public void onUpdateReceived(Update update) {
		try {
			String textFromUser = update.getMessage().getText();
			Long userId = update.getMessage().getFrom().getId();
			Long chatId = update.getMessage().getChatId();
			String userName = update.getMessage().getFrom().getUserName();
			UserSession session = userSessionService.getSession(chatId);

			String loggedMessage = "Message from: " + userId + " - " + userName + ", text: " + textFromUser;

			logger.info(loggedMessage);

			UserRequest userRequest = UserRequest
					.builder()
					.update(update)
					.userSession(session)
					.chatId(chatId)
					.build();

			MessageResponse response = dispatcherService.dispatch(userRequest);

			SendMessage sendMessage = SendMessage.builder()
					.chatId(chatId)
					.text(response.getText())
					.replyMarkup(response.getKeyboard())
					.build();

			this.sendApiMethod(sendMessage);
		} catch (TelegramApiException e) {
			logger.error("Error while send message: ", e);
			throw new RuntimeException(e);
		}
	}

	public void start() throws TelegramApiException {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

		try {
			telegramBotsApi.registerBot(this);
			logger.info("Telegram bot successfully started");
		} catch (TelegramApiException e) {
			logger.error("Failed to connect bot to Telegram API. Check your bot token and username.", e);
		}
	}
}