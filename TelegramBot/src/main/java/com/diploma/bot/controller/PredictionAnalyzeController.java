package com.diploma.bot.controller;

import com.diploma.bot.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static com.diploma.bot.util.Constants.YAHOO_HISTORY_URL;


@RestController
@RequiredArgsConstructor
@RequestMapping("/prediction-analyze")
public class PredictionAnalyzeController {

    private final PredictionService predictionService;

    @GetMapping
    public String getHistoricalData(String value, String action) throws IOException {
        LocalDate currentDate = LocalDate.now();
        LocalDate daysAgo = currentDate.minusDays(37);

        long period1 = daysAgo.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
        long period2 = currentDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC);

        Document doc = Jsoup.connect(String.format(YAHOO_HISTORY_URL, value.toUpperCase(), period1, period2)).get();
        return predictionService.extractHistoricalData(doc, value, action);
    }
}
