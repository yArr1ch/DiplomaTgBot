package com.diploma.bot.controller;

import com.diploma.bot.model.Energy;
import com.diploma.bot.service.EnergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.diploma.bot.util.Constants.YAHOO_FINANCE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/energy")
public class EnergyController {

    private static final String ENERGY = "screener/predefined/sec-ind_sec-largest-equities_energy/";
    private final RestTemplate restTemplate;
    private final EnergyService energyService;

    @GetMapping
    public List<Energy> getEnergies() {
        String result = restTemplate.getForObject(YAHOO_FINANCE_URL + ENERGY, String.class);
        return energyService.getEnergies(result);
    }
}
