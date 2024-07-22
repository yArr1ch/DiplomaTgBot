package com.diploma.bot.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class KeyboardFactory {

    public static ReplyKeyboard selectSectors() {
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row.add("Stocks: Most Active");
        row.add("Stocks: Gainers");
        row.add("Stocks: Losers");
        row.add("Currency");
        row.add("Futures");
        row2.add("Real Estate");
        row2.add("Energy");
        row2.add("Technology");
        row2.add("Finance");
        row2.add("Crypto");
        return new ReplyKeyboardMarkup(List.of(row, row2));
    }

    public static ReplyKeyboard selectSectorsToSetFrequency() {
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row.add("Stocks: Most Active Frequency");
        row.add("Stocks: Gainers Frequency");
        row.add("Stocks: Losers Frequency");
        row2.add("Currency Frequency");
        row2.add("Futures Frequency");
        row2.add("Real Estate Frequency");
        row3.add("Energy Frequency");
        row3.add("Technology Frequency");
        row3.add("Finance Frequency");
        row3.add("Crypto Frequency");
        return new ReplyKeyboardMarkup(List.of(row, row2, row3));
    }

    public static ReplyKeyboard chooseActions() {
        KeyboardRow row = new KeyboardRow();
        row.add("Select Sectors");
        row.add("Analyze"); //all changes that been, on current value or similar and provide explanation
        row.add("Get Prediction"); // predict baseed on data, provide all values
        row.add("Set Frequency");
        row.add("List of existing Frequency");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    //more categories
}
