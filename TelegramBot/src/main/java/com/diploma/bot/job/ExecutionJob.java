package com.diploma.bot.job;

import com.diploma.bot.application.TradeBot;
import com.diploma.bot.config.BotProperties;
import com.diploma.bot.controller.*;
import com.diploma.bot.model.*;
import com.diploma.bot.util.ImageGenerator;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExecutionJob implements Job {

    private final CurrencyController currencyController;
    private final StockController stockController;
    private final FinancialController financialController;
    private final EnergyController energyController;
    private final CryptoController cryptoController;
    private final TechnologyController technologyController;
    private final RealEstateController realEstateController;
    private final FuturesController futuresController;
    private final TradeBot tradeBot;
    private final BotProperties botProperties;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String controllerName = dataMap.getString("controllerName");
        String category = controllerName.split("Controller")[0].toLowerCase();

        switch (controllerName) {
            case "CurrencyController" -> {
                List<Currency> currencies = currencyController.getCurrencies();
                byte[] data = ImageGenerator.generateImageFromCurrencies(currencies);
                scheduleExecution(category, data);
            }
            case "StockControllerMostActive" -> {
                List<Stock> stocks = stockController.getMostActiveStocks();
                byte[] data = ImageGenerator.generateImageFromStocks(stocks);
                scheduleExecution(category, data);
            }
            case "StockControllerGainers" -> {
                List<Stock> stocks = stockController.getGainersStocks();
                byte[] data = ImageGenerator.generateImageFromStocks(stocks);
                scheduleExecution(category, data);
            }
            case "StockControllerLosers" -> {
                List<Stock> stocks = stockController.getLosersStocks();
                byte[] data = ImageGenerator.generateImageFromStocks(stocks);
                scheduleExecution(category, data);
            }
            case "FinancialController" -> {
                List<Finance> finances = financialController.getFinances();
                byte[] data = ImageGenerator.generateImageFromFinances(finances);
                scheduleExecution(category, data);
            }
            case "EnergyController" -> {
                List<Energy> energies = energyController.getEnergies();
                byte[] data = ImageGenerator.generateImageFromEnergies(energies);
                scheduleExecution(category, data);
            }
            case "CryptoController" -> {
                List<Crypto> cryptos = cryptoController.getCrypto();
                byte[] data = ImageGenerator.generateImageFromCrypto(cryptos);
                scheduleExecution(category, data);
            }
            case "TechnologyController" -> {
                List<Technology> technologies = technologyController.getTechnologies();
                byte[] data = ImageGenerator.generateImageFromTechnologies(technologies);
                scheduleExecution(category, data);
            }
            case "RealEstateController" -> {
                List<RealEstate> realEstates = realEstateController.getRealEstates();
                byte[] data = ImageGenerator.generateImageFromRealEstate(realEstates);
                scheduleExecution(category, data);
            }
            case "FuturesController" -> {
                List<Futures> futures = futuresController.getFutures();
                byte[] data = ImageGenerator.generateImageFromFutures(futures);
                scheduleExecution(category, data);
            }
        }
    }

    private void scheduleExecution(String category, byte[] data) {
        tradeBot.scheduledExecution(botProperties.getChatId(), data, category);
    }
}
