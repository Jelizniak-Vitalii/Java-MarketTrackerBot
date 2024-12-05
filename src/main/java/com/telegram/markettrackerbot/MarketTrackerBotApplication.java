package com.telegram.markettrackerbot;

import com.telegram.markettrackerbot.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MarketTrackerBotApplication {
    public static void main(String[] args) throws TelegramApiException {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        // These are two approaches to setting up the environment, with an xml file and java code

        TelegramBot telegramBot = context.getBean(TelegramBot.class);
        telegramBot.start();
    }
}
