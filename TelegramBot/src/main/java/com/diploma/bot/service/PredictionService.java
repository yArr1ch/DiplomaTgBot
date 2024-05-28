package com.diploma.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PredictionService {

    private final RestTemplate restTemplate;

    public String extractHistoricalData(Document doc, String value, String action) {
        Elements rows = doc.select("table tbody tr");

        StringBuilder formattedResult = new StringBuilder();
        formattedResult.append("Date").append("\t").append("Open").append("\t")
                .append("High").append("\t").append("Low").append("\t")
                .append("Close").append("\t").append("Adj Close").append("\t")
                .append("Volume").append("\n");

        for (Element row : rows) {
            Elements columns = row.select("td");
            String date = columns.get(0).text().trim(); // Date
            String open = columns.get(1).text().trim(); // Open
            String high = columns.get(2).text().trim(); // High
            String low = columns.get(3).text().trim(); // Low
            String close = columns.get(4).text().trim(); // Close
            String adjClose = columns.get(5).text().trim(); // Adj Close
            String volume = columns.get(6).text().trim(); // Volume

            // Append the values to the formatted result
            formattedResult.append(date).append("\t")
                    .append(open).append("\t")
                    .append(high).append("\t")
                    .append(low).append("\t")
                    .append(close).append("\t")
                    .append(adjClose).append("\t")
                    .append(volume).append("\n");
        }
        String predictionResponse = returnResponse(formattedResult, action);

        String result;
        if (Objects.equals(action, "predict")) {
            log.info("Returned predicted close price: {}", predictionResponse);

            result = "Predicted close price for " + value + "\n" +
                    predictionResponse.replaceAll("[\"{}]", "").substring(0, 34);
        } else {
            log.info("Returned analyzed statement: {}", predictionResponse);

            result = "Analysis for " + value + "\n" +
                    predictionResponse.replaceAll("[\"{}]", "");
        }
        return result;
    }

    private String returnResponse(StringBuilder result, String action) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(result.toString(), headers);

        return restTemplate.postForObject(
                String.format("http://127.0.0.1:5000/%s", action), requestEntity, String.class);
    }
}
