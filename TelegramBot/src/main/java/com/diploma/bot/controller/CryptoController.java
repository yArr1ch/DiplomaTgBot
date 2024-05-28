package com.diploma.bot.controller;

import com.diploma.bot.model.Crypto;
import com.diploma.bot.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.diploma.bot.util.Constants.YAHOO_FINANCE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crypto")
public class CryptoController {

    private static final String CRYPTO = "crypto/";
    private final RestTemplate restTemplate;
    private final CryptoService cryptoService;

    @GetMapping
    public List<Crypto> getCrypto() {
        String result = restTemplate.getForObject(YAHOO_FINANCE_URL + CRYPTO, String.class);
        return cryptoService.getCrypto(result);
    }
}
