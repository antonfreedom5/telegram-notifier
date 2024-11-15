package com.labinvent.telegram_notifier.service;

import com.labinvent.telegram_notifier.TelegramBot;
import com.labinvent.telegram_notifier.config.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;

@Service
@RequiredArgsConstructor
public class SenderService {

    private final BotConfig botConfig;
    private final TelegramBot telegramBot;

    @SneakyThrows
    public void send(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(botConfig.getChatId());
        sendMessage.setText(message);
        sendMessage.setParseMode("html");
        telegramBot.execute(sendMessage);
    }

    @SneakyThrows
    public void send(MultipartFile multipartFile) {
        File file = new File("/tmp/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(botConfig.getChatId());
        sendDocument.setDocument(new InputFile(file));
        telegramBot.execute(sendDocument);
        file.delete();
    }
}
