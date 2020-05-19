package org.dentago.bot;

import org.dentago.domain.Constants;
import org.dentago.service.KeyboardService;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVenue;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ResponseHandler {
    private final MessageSender sender;

    public ResponseHandler(MessageSender sender, DBContext db) {
        this.sender = sender;
    }

    public void replyToCallBackButtons(long chatId, String buttonId) {
        if (Constants.CALL_BUTTON.equals(buttonId)) {
            replyToCallButton(chatId);
        }
    }

    public void replyToContactButton(long chatId) {
        sendTextMessageWithInlineKeyboard(chatId, Constants.DEFAULT_REPLY, KeyboardService.getContactsButtons());
    }

    public void replyToLocationButton(long chatId) {
        SendVenue venue = new SendVenue()
                .setChatId(chatId)
                .setTitle(Constants.VENUE_TITLE)
                .setAddress(Constants.ADDRESS)
                .setLatitude(Constants.LATITUDE)
                .setLongitude(Constants.LONGITUDE);

        sendMessage(venue);
    }

    public void replyToCallButton(long chatId) {
        SendContact contact = new SendContact()
                .setChatId(chatId)
                .setFirstName("Андрей")
                .setLastName("Головко")
                .setPhoneNumber("+380978592859");

        sendMessage(contact);
    }

    public void replyToStart(long chatId) {
        sendTextMessage(chatId, Constants.GREETING_MESSAGE);

        sendTextMessageWithMenuKeyboard(chatId);
    }

    private void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage()
                .setText(text)
                .setChatId(chatId);

        sendMessage(message);
    }

    private void sendTextMessageWithInlineKeyboard(long chatId, String text, ReplyKeyboard keyboard) {
        SendMessage message = new SendMessage()
                .setText(text)
                .setChatId(chatId)
                .setReplyMarkup(keyboard);

        sendMessage(message);
    }

    private void sendTextMessageWithMenuKeyboard(long chatId) {
        SendMessage message = new SendMessage()
                .setText(Constants.DEFAULT_REPLY)
                .setChatId(chatId)
                .setReplyMarkup(KeyboardService.getMenuButtons());

        sendMessage(message);
    }

    private void sendMessage(BotApiMethod<Message> message) {
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToSharePhone(Long chatId) {
        SendMessage message = new SendMessage()
                .setText(Constants.SEND_PHONE)
                .setChatId(chatId)
                .setReplyMarkup(KeyboardService.getRequestPhoneButtons());

        sendMessage(message);
    }
}
