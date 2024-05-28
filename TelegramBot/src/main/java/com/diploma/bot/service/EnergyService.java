package com.diploma.bot.service;

import com.diploma.bot.model.Energy;
import com.diploma.bot.util.DataParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyService {

    public List<Energy> getEnergies(String data) {
        return DataParser.parseEnergies(data);
    }
}
