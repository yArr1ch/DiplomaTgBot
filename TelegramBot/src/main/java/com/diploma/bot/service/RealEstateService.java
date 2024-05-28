package com.diploma.bot.service;

import com.diploma.bot.model.RealEstate;
import com.diploma.bot.util.DataParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealEstateService {

    public List<RealEstate> getRealEstate(String result) {
        return DataParser.parseRealEstate(result);
    }
}
