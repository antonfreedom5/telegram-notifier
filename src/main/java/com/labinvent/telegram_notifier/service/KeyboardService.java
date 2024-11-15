package com.labinvent.telegram_notifier.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class KeyboardService {

    private final Map<String, String> environments = Map.of(
            "Testjira status", "http://math-dev:8500/eureka/v2/apps",
            "Lserver2 status", "http://math-srv2:8500/eureka/v2/apps",
            "Lserver status", "http://math-server:8500/eureka/v2/apps"
    );

    public InlineKeyboardMarkup buildKeyboardMarkup() {
        List<List<InlineKeyboardButton>> keyboardRows = buildKeyboardRows(environments);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardRows);
        return inlineKeyboardMarkup;
    }

    private List<List<InlineKeyboardButton>> buildKeyboardRows(Map<String, String> environments) {
        return environments.entrySet().stream()
                .map(entry -> {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(entry.getKey());
                    button.setCallbackData("%s|%s".formatted(entry.getKey(), entry.getValue()));
                    return List.of(button);
                })
                .collect(toList());
    }
}
