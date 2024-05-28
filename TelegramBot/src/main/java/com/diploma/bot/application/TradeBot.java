package com.diploma.bot.application;

import com.diploma.bot.config.BotProperties;
import com.diploma.bot.service.QuartzService;
import com.diploma.bot.controller.*;
import com.diploma.bot.service.FrequencyOfPullingService;
import com.diploma.bot.service.NewsService;
import com.diploma.bot.util.ImageGenerator;
import com.diploma.bot.util.KeyboardFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class TradeBot extends TelegramLongPollingBot implements BotResponse {

    private final BotProperties botProperties;
    private final StockController stockController;
    private final CurrencyController currencyController;
    private final FuturesController futuresController;
    private final RealEstateController realEstateController;
    private final EnergyController energyController;
    private final TechnologyController technologyController;
    private final FinancialController financialController;
    private final CryptoController cryptoController;
    private final PredictionAnalyzeController predictionAnalyzeController;
    private final QuartzService quartzService;
    private final NewsService newsService;
    private final FrequencyOfPullingService frequencyOfPullingService;
    private boolean botStarted = false;
    private boolean selectingSectors = false;
    private boolean waitingForFrequencyValue = false;
    private boolean waitingForPredictionValue = false;
    private boolean waitingForAnalyzeValue = false;
    @Getter
    private String userId;
    private String frequency;

    public TradeBot(BotProperties botProperties, StockController stockController, CurrencyController currencyController,
                    FrequencyOfPullingService frequencyOfPullingService,
                    QuartzService quartzService,
                    FuturesController futuresController,
                    NewsService newsService,
                    RealEstateController realEstateController,
                    EnergyController energyController,
                    TechnologyController technologyController,
                    FinancialController financialController,
                    CryptoController cryptoController,
                    PredictionAnalyzeController predictionAnalyzeController) {
        super(botProperties.getToken());
        this.botProperties = botProperties;
        this.stockController = stockController;
        this.currencyController = currencyController;
        this.frequencyOfPullingService = frequencyOfPullingService;
        this.quartzService = quartzService;
        this.futuresController = futuresController;
        this.newsService = newsService;
        this.realEstateController = realEstateController;
        this.energyController = energyController;
        this.technologyController = technologyController;
        this.financialController = financialController;
        this.cryptoController = cryptoController;
        this.predictionAnalyzeController = predictionAnalyzeController;
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    //default one
    @Override
    public void sendResponse(Long chatId, String message) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId.toString());
        response.setText(message);
        sendActionsKeyboard(chatId);

        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            this.userId = update.getMessage().getFrom().getId().toString();

            if (!botStarted) {
                handleBotStart(chatId);
            } else if (selectingSectors) {
                try {
                    handleSubActionSelection(chatId, text);
                } catch (SchedulerException e) {
                    throw new RuntimeException(e);
                }
            } else if (waitingForFrequencyValue) {
                processReceivedFrequencyValue(chatId, text);
            } else if (waitingForPredictionValue) {
                try {
                    processReceivedPredictedValue(chatId, text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (waitingForAnalyzeValue) {
                try {
                    processReceivedAnalyzedValue(chatId, text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    handleActionSelection(chatId, text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void handleBotStart(Long chatId) {
        botStarted = true;
        sendActionsKeyboard(chatId);
        runExistingJobs();
    }

    private void handleSubActionSelection(Long chatId, String text) throws SchedulerException {
        selectingSectors = false;
        switch (text) {
            case "Stocks: Most Active", "Stocks: Gainers", "Stocks: Losers", "Currency", "Futures",
                 "Real Estate", "Energy", "Technology", "Finance", "Crypto" ->
                    chooseSectors(chatId, text);

            case "Stocks: Most Active Frequency", "Stocks: Gainers Frequency", "Stocks: Losers Frequency",
                 "Currency Frequency", "Futures Frequency", "Real Estate Frequency", "Energy Frequency",
                 "Technology Frequency", "Finance Frequency", "Crypto Frequency" ->
                    setFrequencyOfCurrentSector(text, chatId);

            default -> {
            }
        }
    }

    private void handleActionSelection(Long chatId, String text) throws IOException {
        switch (text) {
            case "Select Sectors" -> {
                sendResponse(chatId, KeyboardFactory.selectSectors(), "Select sectors");
                selectingSectors = true;
            }
            case "Set Frequency" -> {
                requestFrequency(chatId);
            }
            case "Analyze" -> {
                requestAnalyze(chatId);
            }
            case "Get Prediction" -> {
                requestPrediction(chatId);
            }
            default -> {
            }
        }
    }
    private void requestAnalyze(Long chatId) {
        sendResponse(chatId, "Please enter the symbol of sector to analyze:");
        waitingForAnalyzeValue = true;
    }

    private void requestPrediction(Long chatId) {
        sendResponse(chatId, "Please enter the symbol of sector to predict:");
        waitingForPredictionValue = true;
    }

    private void requestFrequency(Long chatId) {
        sendResponse(chatId, "Please enter the frequency in minutes:");
        waitingForFrequencyValue = true;
    }

    public void processReceivedPredictedValue(Long chatId, String value) throws IOException {
        String predictedPrice = predictionAnalyzeController.getHistoricalData(value, "predict");
        waitingForPredictionValue = false;
        selectingSectors = true;
        sendResponse(chatId, predictedPrice);
    }

    public void processReceivedAnalyzedValue(Long chatId, String value) throws IOException {
        String analyzedAction = predictionAnalyzeController.getHistoricalData(value, "analyze");
        waitingForAnalyzeValue = false;
        selectingSectors = true;
        sendResponse(chatId, analyzedAction);
    }

    public void processReceivedFrequencyValue(Long chatId, String value) {
        this.frequency = value;
        waitingForFrequencyValue = false;
        selectingSectors = true;
        sendResponse(chatId, KeyboardFactory.selectSectorsToSetFrequency(), "Frequency was selected");
    }

    public void chooseSectors(Long chatId, String sectors) {
        switch (sectors) {
            case "Stocks: Most Active" -> sendImageResponse(chatId,
                    ImageGenerator.generateImageFromStocks(stockController.getMostActiveStocks()),
                    "MostActiveStocks.png");

            case "Stocks: Gainers" -> sendImageResponse(chatId,
                    ImageGenerator.generateImageFromStocks(stockController.getGainersStocks()),
                    "MostActiveStocks.png");

            case "Stocks: Losers" -> sendImageResponse(chatId,
                    ImageGenerator.generateImageFromStocks(stockController.getLosersStocks()),
                    "MostActiveStocks.png");

            case "Currency" -> sendImageResponse(chatId,
                    ImageGenerator.generateImageFromCurrencies(currencyController.getCurrencies()),
                    "Currencies.png");

            case "Futures" -> sendImageResponse(chatId,
                    ImageGenerator.generateImageFromFutures(futuresController.getFutures()),
                    "Futures.png");

            case "Real Estate" -> sendImageResponse(chatId,
                    ImageGenerator.generateImageFromRealEstate(realEstateController.getRealEstates()),
                    "RealEstate.png");

            case "Energy" -> sendImageResponse(chatId,
                    ImageGenerator.generateImageFromEnergies(energyController.getEnergies()),
                    "Energy.png");

            case "Technology" -> sendImageResponse(chatId,
                    ImageGenerator.generateImageFromTechnologies(technologyController.getTechnologies()),
                    "Technology.png");

            case "Finance" -> sendImageResponse(chatId,
                    ImageGenerator.generateImageFromFinances(financialController.getFinances()),
                    "Finance.png");

            case "Crypto" -> sendImageResponse(chatId,
                    ImageGenerator.generateImageFromCrypto(cryptoController.getCrypto()),
                    "Crypto.png");

            default -> {
            }
        }
    }

    @Override
    public void sendActionsKeyboard(Long chatId) {
        sendResponse(chatId, KeyboardFactory.chooseActions(), "Please choose some action");
    }

    // for category selection
    @Override
    public void sendResponse(Long chatId, ReplyKeyboard key, String text) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId.toString());
        response.setText(text);
        response.setReplyMarkup(key);

        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendImageResponse(Long chatId, byte[] bytes, String fileName) {
        log.info("Sending image of {} to chat with id: {}", fileName.split("\\.")[0], chatId);
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            InputFile inputFile = new InputFile().setMedia(inputStream, fileName);

            SendPhoto photo = new SendPhoto(chatId.toString(), inputFile);
            execute(photo);
            sendActionsKeyboard(chatId);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setFrequencyOfCurrentSector(String category, Long chatId) {
        //frequencyOfPullingService.createFrequencyRecord(userId, category, frequency, String.valueOf(chatId));
        quartzService.setFrequencyOfCurrentValue(category, Integer.parseInt(frequency), userId);

        sendResponse(chatId, "Frequency of " + frequency + " was successfully set for " + category);
        sendActionsKeyboard(chatId);
        log.warn("values were passed to db {}, {}, {}", userId, category, frequency);
    }

    public void scheduledExecution(Long chatId, byte[] data, String category) {
        sendImageResponse(chatId, data, category + ".png");
    }

    private void runExistingJobs() {
        quartzService.runExistingJobs(userId);
    }

    @Scheduled(fixedRate = 600000) // Run every 10 minutes
    private void sendNews() throws IOException {
        Map<String, String> news = newsService.runAllNews();
        Set<String> links = news.keySet();

        links.forEach(l -> {
            SendMessage response = new SendMessage();
            response.setChatId(botProperties.getChatId());
            response.setText(l);

            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }
}
