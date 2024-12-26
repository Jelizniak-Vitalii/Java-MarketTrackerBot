package com.telegram.markettrackerbot.bot;

import com.telegram.markettrackerbot.models.UserRequestInfo;
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

import java.io.IOException;

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
      UserRequestInfo userRequestInfo = dispatcherService.getUserRequestInfo(update);
			UserSession session = userSessionService.getSession(userRequestInfo.getChatId());
      session.setText(userRequestInfo.getText());
      session.setUserRequestInfo(userRequestInfo);

			UserRequest userRequest = UserRequest
        .builder()
        .update(update)
        .userSession(session)
        .chatId(userRequestInfo.getChatId())
        .build();

			MessageResponse response = dispatcherService.dispatch(userRequest);

			SendMessage sendMessage = SendMessage.builder()
        .chatId(userRequestInfo.getChatId())
        .text(response.getText())
        .replyMarkup(response.getKeyboard())
        .build();

      sendMessage.setReplyMarkup(response.getInlineKeyboard());

			this.sendApiMethod(sendMessage);

      String loggedMessage = "Message from: " + userRequestInfo.getUserId() + " - " + userRequestInfo.getUserName() + ", text: " + userRequestInfo.getText();
      logger.info(loggedMessage);
		} catch (TelegramApiException e) {
      logger.error("Error while send message: ", e);
		} catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

	public void start() throws TelegramApiException {
    try {
      TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

			telegramBotsApi.registerBot(this);

			logger.info("Telegram bot successfully started");
		} catch (TelegramApiException e) {
			logger.error("Failed to connect bot to Telegram API. Check your bot token and username.", e);
		}
	}
}
