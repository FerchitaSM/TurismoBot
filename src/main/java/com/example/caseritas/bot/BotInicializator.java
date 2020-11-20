package com.example.caseritas.bot;


import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.caseritas.bl.BotBl;
import com.example.caseritas.bl.PosibleLugarBl;
import com.example.caseritas.bl.UserBl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class BotInicializator {
    private static final Logger log = LoggerFactory.getLogger(BotInicializator.class);

    BotBl botBl;
    UserBl userBl;
    PosibleLugarBl posibleLugarBl;

    @Autowired
    public BotInicializator(BotBl botBl, UserBl userBl, PosibleLugarBl posibleLugarBl) {
        this.botBl = botBl;
        this.userBl = userBl;
        this.posibleLugarBl = posibleLugarBl;
    }


    @PostConstruct
    public void levantando_bot() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new BotMain(botBl, userBl, posibleLugarBl));
            log.info("Bot levantado");
        } catch (TelegramApiException e) {
            log.info("Bot NO levantado");
            e.printStackTrace();
        }

    }
}
