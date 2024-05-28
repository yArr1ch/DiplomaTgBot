package com.diploma.bot.service;

import java.io.IOException;
import java.util.Map;

public interface NewsService {

    void getLatestYahooNews() throws IOException;

    void getStockMarketNews() throws IOException;

    void getEarningsNews() throws IOException;

    void getPoliticsNews() throws IOException;

    void getEconomicNews() throws IOException;

    void getPersonalFinanceNews() throws IOException;

    void getCryptoNews() throws IOException;

    Map<String, String> runAllNews() throws IOException;
}
