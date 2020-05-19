package org.dentago.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static org.dentago.domain.Constants.*;

public class KeyboardService {

    public static ReplyKeyboard getContactsButtons() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        rowInline.add(new InlineKeyboardButton()
                .setText(WRITE_BUTTON)
                .setUrl("https://t.me/fugrusha"));

        rowInline.add(new InlineKeyboardButton()
                .setText(CALL_BUTTON)
                .setCallbackData(CALL_BUTTON));

        rowsInline.add(rowInline);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ReplyKeyboard getMenuButtons() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton(APPLICATION_BUTTON));
        keyboardFirstRow.add(new KeyboardButton(CONTACT_BUTTON));
        keyboardFirstRow.add(new KeyboardButton(LOCATION_BUTTON));

        keyboard.add(keyboardFirstRow);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static ReplyKeyboard getRequestPhoneButtons() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton(SHARE_NUMBER).setRequestContact(true));
        keyboardFirstRow.add(new KeyboardButton(CANCEL_BUTTON));

        keyboard.add(keyboardFirstRow);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
}
