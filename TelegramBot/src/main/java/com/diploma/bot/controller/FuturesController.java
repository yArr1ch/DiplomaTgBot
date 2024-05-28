package com.diploma.bot.controller;

import com.diploma.bot.model.Futures;
import com.diploma.bot.service.FuturesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.diploma.bot.util.Constants.YAHOO_FINANCE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commodities")
public class FuturesController {

    private static final String COMMODITIES = "commodities/";
    private final RestTemplate restTemplate;
    private final FuturesService futuresService;

    @GetMapping
    public List<Futures> getFutures() {
        String result = restTemplate.getForObject(YAHOO_FINANCE_URL + COMMODITIES, String.class);
        return futuresService.getFutures(result);
    }
}
