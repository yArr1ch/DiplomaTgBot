package com.diploma.bot.service;

import com.diploma.bot.model.Stock;
import com.diploma.bot.util.DataParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    public List<Stock> getMostActiveStocks(String result) {
        return DataParser.parseStocks(result);
    }

    public List<Stock> getGainersStocks(String result) {
        return DataParser.parseStocks(result);
    }

    public List<Stock> getLosersStocks(String result) {
        return DataParser.parseStocks(result);
    }
}
