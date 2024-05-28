package com.diploma.bot.service;

import com.diploma.bot.model.Futures;
import com.diploma.bot.util.DataParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuturesService {

    public List<Futures> getFutures(String data) {
        return DataParser.parseFutures(data);
    }
}
