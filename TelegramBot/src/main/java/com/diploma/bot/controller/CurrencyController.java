package com.diploma.bot.controller;

import com.diploma.bot.model.Currency;
import com.diploma.bot.service.CurrencyService;
import com.diploma.bot.service.GeneralSectorsService;
import com.diploma.bot.util.DataParser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.diploma.bot.util.Constants.YAHOO_FINANCE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currencies")
public class CurrencyController {

    private static final String CURRENCIES = "currencies/";
    private final RestTemplate restTemplate;
    //private final CurrencyService currencyService;
    private final GeneralSectorsService<Currency> currencyService;

    @GetMapping
    public List<Currency> getCurrencies() {
        String result = restTemplate.getForObject(YAHOO_FINANCE_URL + CURRENCIES, String.class);
        return currencyService.getValues(result, DataParser::parseCurrencies);
        //return currencyService.getCurrencies(result);
    }
}
