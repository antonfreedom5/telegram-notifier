package com.labinvent.telegram_notifier.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String name;
    private String token;
    private Long chatId;
}