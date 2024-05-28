package com.diploma.bot.controller;

import com.diploma.bot.model.Energy;
import com.diploma.bot.model.Finance;
import com.diploma.bot.service.EnergyService;
import com.diploma.bot.service.FinancialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.diploma.bot.util.Constants.YAHOO_FINANCE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/financial")
public class FinancialController {

    private static final String FINANCIAL = "screener/predefined/sec-ind_sec-largest-equities_financial-services/";
    private final RestTemplate restTemplate;
    private final FinancialService financialService;

    @GetMapping
    public List<Finance> getFinances() {
        String result = restTemplate.getForObject(YAHOO_FINANCE_URL + FINANCIAL, String.class);
        return financialService.getFinances(result);
    }
}
