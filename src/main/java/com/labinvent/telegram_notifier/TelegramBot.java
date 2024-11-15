package com.labinvent.telegram_notifier;

import com.labinvent.telegram_notifier.config.BotConfig;
import com.labinvent.telegram_notifier.service.KeyboardService;
import com.labinvent.telegram_notifier.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final KeyboardService keyboardService;
    private final RestTemplateService restTemplateService;

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                switch (update.getMessage().getText()) {
                    case "/start" -> {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(botConfig.getChatId());
                        sendMessage.setText("Choose environment");
                        sendMessage.setReplyMarkup(keyboardService.buildKeyboardMarkup());
                        execute(sendMessage);
                    }
                    default -> {
                        SendPhoto sendPhoto = new SendPhoto();
                        sendPhoto.setPhoto(new InputFile(new File("/opt/telegram-notifier/mem.jpg")));
                        sendPhoto.setChatId(botConfig.getChatId());
                        execute(sendPhoto);
                    }
                }
            }
        }

        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            String message = restTemplateService.get(data);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(botConfig.getChatId());
            sendMessage.setText(message);
            sendMessage.setParseMode("html");
            execute(sendMessage);
        }
    }
}