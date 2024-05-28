package com.diploma.bot.controller;

import com.diploma.bot.model.Technology;
import com.diploma.bot.service.TechnologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.diploma.bot.util.Constants.YAHOO_FINANCE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/technologies")
public class TechnologyController {

    private static final String TECHNOLOGIES = "screener/predefined/sec-ind_sec-largest-equities_technology/";
    private final RestTemplate restTemplate;
    private final TechnologyService technologyService;

    @GetMapping
    public List<Technology> getTechnologies() {
        String result = restTemplate.getForObject(YAHOO_FINANCE_URL + TECHNOLOGIES, String.class);
        return technologyService.getTechnologies(result);
    }
}
