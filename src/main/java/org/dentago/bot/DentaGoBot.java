package org.dentago.bot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.dentago.domain.Constants.*;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class DentaGoBot extends AbilityBot {

    private final ResponseHandler responseHandler;

    public DentaGoBot() {
        super(BOT_TOKEN, BOT_USERNAME);
        responseHandler = new ResponseHandler(sender, db);
    }

    @Override
    public int creatorId() {
        return CREATOR_ID;
    }

    public Ability sayHelloWorld() {
        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Hello world!", ctx.chatId()))
                .build();
    }

    public Ability replyToStart() {
        return Ability
                .builder()
                .name("start")
                .info(START_DESCRIPTION)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> responseHandler.replyToStart(ctx.chatId()))
                .build();
    }

    public Reply replyToCallbackButton() {
        Consumer<Update> action = upd -> responseHandler.replyToCallBackButtons(getChatId(upd), upd.getCallbackQuery().getData());
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }

//    public Reply replyToCallButton() {
//        Consumer<Update> action = upd -> responseHandler.replyToCallButton(getChatId(upd));
//        return Reply.of(action, hasMessageWith(CALL_BUTTON));
//    }

    public Reply replyToContactButton() {
        Consumer<Update> action = upd -> responseHandler.replyToContactButton(getChatId(upd));
        return Reply.of(action, hasMessageWith(CONTACT_BUTTON));
    }

    public Reply replyToLocationButton() {
        Consumer<Update> action = upd -> responseHandler.replyToLocationButton(getChatId(upd));
        return Reply.of(action, hasMessageWith(LOCATION_BUTTON));
    }

    @NotNull
    private Predicate<Update> hasMessageWith(String msg) {
        return upd -> upd.getMessage().getText().equalsIgnoreCase(msg);
    }

    private Predicate<Update> hasContact() {
        return upd -> upd.getMessage().getContact() != null;
    }

    @NotNull
    private Predicate<Update> isReplyToMessage(String message) {
        return upd -> {
            Message reply = upd.getMessage().getReplyToMessage();
            return reply.hasText() && reply.getText().equalsIgnoreCase(message);
        };
    }
//
//    private Predicate<Update> isReplyToBot() {
//        return upd -> upd.getMessage().getReplyToMessage().getFrom().getUserName().equalsIgnoreCase(getBotUsername());
//    }
//
    public ReplyFlow directionFlow() {

        Reply saidThanks = Reply.of(upd -> silent.send(FINISH_APPLICATION, getChatId(upd)), isReplyToMessage("enter desirable date"));

        // todo save date to db
        ReplyFlow requestDate = ReplyFlow.builder(db)
                .action(upd -> silent.send("enter desirable date", getChatId(upd)))
                .onlyIf(hasMessageWith("date"))
                .next(saidThanks)
                .build();

        // todo save phone to db
        ReplyFlow replyToGetPhone = ReplyFlow.builder(db)
                .action(upd -> upd.getMessage().getContact())
                .onlyIf(hasContact())
                .next(requestDate)
                .build();

        ReplyFlow requestPhone = ReplyFlow.builder(db)
                .action(upd -> responseHandler.replyToSharePhone(getChatId(upd)))
                .next(replyToGetPhone)
                .build();

        return ReplyFlow.builder(db)
                .action(upd -> silent.send(START_APPLICATION, getChatId(upd)))
                .onlyIf(hasMessageWith(APPLICATION_BUTTON))
                .next(requestPhone)
                .build();
    }
}
