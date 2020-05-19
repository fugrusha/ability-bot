package org.dentago.domain;

public final class Constants {

    public static final String BOT_TOKEN = System.getenv("token");
    public static final String BOT_USERNAME = "test_dental_telebot";
    public static final int CREATOR_ID = 221802972;

    public static final String VENUE_TITLE = "Адрес клиники DentaGo";
    public static final String ADDRESS = "г. Днепр, смт. Таромское \r\n ул. Академика Кисловского 1-А";
    public static final float LONGITUDE = 34.808175f;
    public static final float LATITUDE = 48.434606f;

    // descriptions
    public static final String START_DESCRIPTION = "Start using the bot";

    // buttons
    public static final String WRITE_BUTTON = "Написать";
    public static final String CALL_BUTTON = "Позвонить";
    public static final String APPLICATION_BUTTON = "Запись";
    public static final String CONTACT_BUTTON = "Задать вопрос";
    public static final String LOCATION_BUTTON = "Как добраться?";
    public static final String SHARE_NUMBER = "Отправить мой номер";
    public static final String CANCEL_BUTTON = "Назад";

    // messages
    public static final String DEFAULT_REPLY = "Вы можете использовать следующие команды";
    public static final String GREETING_MESSAGE = "Меня зовут Молочный Зуб, я буду твоим помощником.\r\n"
            + "Для общения со мной используйте какую-либо из команд ниже.";

    public static final String START_APPLICATION = "Для записи Вам необходимо выполнить (завершить) регистрацию.";
    public static final String FINISH_APPLICATION = "Ваша заявка принята! В ближайшее время с ваши свяжется наш администратор.";
    public static final String REQUEST_PHONE = "Чтобы мы могли с вами связаться поделитесь вашим контактым телефоном.";
    public static final String SEND_PHONE = "Нажмите отправить номер";

}
