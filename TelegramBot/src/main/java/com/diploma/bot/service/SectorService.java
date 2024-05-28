package com.diploma.bot.service;

import java.util.List;

public interface SectorService<T> {

    List<T> getValues(String data);
}
