package com.diploma.bot.application;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;


public interface BotResponse {

    void sendActionsKeyboard(Long chatId);

    void sendResponse(Long chatId, String message);

    void sendResponse(Long chatId, ReplyKeyboard key, String text);

    void sendImageResponse(Long chatId, byte[] bytes, String fileName);
}