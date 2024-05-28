package com.diploma.bot.controller;

import com.diploma.bot.model.Stock;
import com.diploma.bot.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.diploma.bot.util.Constants.YAHOO_FINANCE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController {

    private static final String MOST_ACTIVE = "most-active/";
    private static final String LOSERS = "losers/";
    private static final String GAINERS = "gainers/";
    private final RestTemplate restTemplate;
    private final StockService stockService;

    @GetMapping("/most-active")
    public List<Stock> getMostActiveStocks() {
        String result = restTemplate.getForObject(YAHOO_FINANCE_URL + MOST_ACTIVE, String.class);
        return stockService.getMostActiveStocks(result);
    }

    @GetMapping("/gainers")
    public List<Stock> getGainersStocks() {
        String result = restTemplate.getForObject(YAHOO_FINANCE_URL + GAINERS, String.class);
        return stockService.getGainersStocks(result);
    }

    @GetMapping("/losers")
    public List<Stock> getLosersStocks() {
        String result = restTemplate.getForObject(YAHOO_FINANCE_URL + LOSERS, String.class);
        return stockService.getLosersStocks(result);
    }
}

