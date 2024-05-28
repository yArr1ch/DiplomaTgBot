package com.diploma.bot.controller;

import com.diploma.bot.model.RealEstate;
import com.diploma.bot.service.RealEstateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.diploma.bot.util.Constants.YAHOO_FINANCE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/real-estate")
public class RealEstateController {

    private static final String REAL_ESTATE = "screener/predefined/sec-ind_sec-largest-equities_real-estate/";
    private final RestTemplate restTemplate;
    private final RealEstateService realEstateService;

    @GetMapping
    public List<RealEstate> getRealEstates() {
        String result = restTemplate.getForObject(YAHOO_FINANCE_URL + REAL_ESTATE, String.class);
        return realEstateService.getRealEstate(result);
    }
}
