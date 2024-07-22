package com.diploma.bot.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class GeneralSectorsService<T> {

    public List<T> getValues(String data, Function<String, List<T>> parseFunction) {
        return parseFunction.apply(data);
    }
}
