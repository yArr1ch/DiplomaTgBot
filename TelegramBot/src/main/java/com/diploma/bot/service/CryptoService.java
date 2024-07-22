package com.diploma.bot.service;

import com.diploma.bot.model.Crypto;
import com.diploma.bot.util.DataParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoService {

    public List<Crypto> getCrypto(String data) {
        return DataParser.parseCrypto(data);
    }
}
