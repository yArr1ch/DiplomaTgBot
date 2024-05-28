package com.diploma.bot.service;

import com.diploma.bot.model.Currency;
import com.diploma.bot.util.DataParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    public List<Currency> getCurrencies(String data) {
        return DataParser.parseCurrencies(data);
    }
}
