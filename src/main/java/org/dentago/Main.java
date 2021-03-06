package org.dentago;

import org.dentago.bot.DentaGoBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new DentaGoBot());
            System.out.println("Bot started!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
