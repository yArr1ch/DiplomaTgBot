package com.diploma.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TelegramBotConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

