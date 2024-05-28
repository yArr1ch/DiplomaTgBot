package com.diploma.bot.service;

import com.diploma.bot.model.Finance;
import com.diploma.bot.util.DataParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialService {

    public List<Finance> getFinances(String data) {
        return DataParser.parseFinances(data);
    }
}
