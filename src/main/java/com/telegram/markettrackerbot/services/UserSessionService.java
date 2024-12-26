package com.telegram.markettrackerbot.services;

import com.telegram.markettrackerbot.models.UserSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserSessionService {
	private final Map<Long, UserSession> userSessionMap = new HashMap<>();

	public UserSession getSession(Long chatId) {
		return userSessionMap.getOrDefault(
			chatId,
			UserSession
				.builder()
				.chatId(chatId)
				.build()
		);
	}

	public UserSession saveSession(Long chatId, UserSession session) {
		return userSessionMap.put(chatId, session);
	}
}
