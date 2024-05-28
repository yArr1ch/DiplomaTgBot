package com.diploma.bot.service;

import com.diploma.bot.model.Technology;
import com.diploma.bot.util.DataParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {

    public List<Technology> getTechnologies(String data) {
        return DataParser.parseTechnologies(data);
    }
}
