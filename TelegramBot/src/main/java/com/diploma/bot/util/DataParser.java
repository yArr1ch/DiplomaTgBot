package com.diploma.bot.util;

import com.diploma.bot.model.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DataParser {

    public static List<Stock> parseStocks(String html) {
        log.info("Converting incoming html into stocks......");
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("td[aria-label]");

        List<Stock> stocks = new ArrayList<>();
        Stock stock = null;

        for (int e = 0; e < elements.size(); e++) {
            String value = elements.get(e).text().trim();

            if (e % 10 == 0) {
                stock = new Stock();
                stocks.add(stock);
            } else {
                int num = stocks.size() - 1;
                switch (e % 10) {
                    case 1 -> {
                        Elements symbolElement = doc.select("td[aria-label=Symbol] a[data-test=quoteLink]");
                        String symbol = symbolElement.text().trim();
                        String[] symbols = symbol.split(" ");
                        stock.setSymbol(symbols[num]);
                        stock.setCompanyName(value);
                    }
                    //case 1 -> stock.setCompanyName(value);
                    case 3 -> stock.setPrice(value);
                    case 4 -> stock.setChange(value);
                    case 5 -> stock.setChangePercentage(value);
                    case 6 -> stock.setVolume(value);
                    case 7 -> stock.setAverageVolume(value);
                    case 8 -> stock.setMarketCap(value);
                    case 9 -> {
                        Elements peRatioElements = doc.select("td[aria-label='PE Ratio (TTM)']");
                        String peRatioValue = peRatioElements.text().trim();
                        String[] values = peRatioValue.split(" ");
                        stock.setPeRatio(values[num]);
                    }
                    default -> {
                    }
                }
            }
        }
        return stocks;
    }

    public static List<Currency> parseCurrencies(String html) {
        log.info("Converting incoming html into currencies......");
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("tr.simpTblRow");

        List<Currency> currencies = new ArrayList<>();

        for (Element e : elements) {
            Currency currency = new Currency();

            currency.setSymbol(e.select("td[aria-label=Symbol] a").first().text().trim());
            currency.setName(e.select("td[aria-label=Name]").first().text().trim());
            currency.setLastPrice(e.select("td[aria-label='Last Price']").first().text().trim());
            currency.setChange(e.select("td[aria-label=Change] span").first().text().trim());
            currency.setChangePercentage(e.select("td[aria-label='% Change'] span").first().text().trim());

            currencies.add(currency);
        }
        return currencies;
    }


    public static List<Futures> parseFutures(String html) {
        log.info("Converting incoming html into futures......");
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("tbody tr");

        List<Futures> futures = new ArrayList<>();

        for (Element e : elements) {
            Futures future = new Futures();

            future.setSymbol(e.select("td[aria-label=Symbol] a").text().trim());
            future.setName(e.select("td[aria-label=Name]").text().trim());
            future.setLastPrice(e.select("td[aria-label='Last Price']").text().trim());
            future.setMarketTime(e.select("td[aria-label=Market Time]").text().trim());
            future.setChange(e.select("td[aria-label=Change] span").text().trim());
            future.setChangePercentage(e.select("td[aria-label='% Change'] span").text().trim());
            future.setVolume(e.select("td[aria-label=Volume]").text().trim());

            futures.add(future);
        }
        return futures;
    }

    public static List<RealEstate> parseRealEstate(String html) {
        log.info("Converting incoming html into real estate......");
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("td[aria-label]");

        List<RealEstate> realEstates = new ArrayList<>();
        RealEstate realEstate = null;

        for (int e = 0; e < elements.size(); e++) {
            String value = elements.get(e).text().trim();

            if (e % 10 == 0) {
                realEstate = new RealEstate();
                realEstates.add(realEstate);
            } else {
                int num = realEstates.size() - 1;
                switch (e % 10) {
                    case 1 -> {
                        Elements symbolElement = doc.select("td[aria-label=Symbol] a[data-test=quoteLink]");
                        String symbol = symbolElement.text().trim();
                        String[] symbols = symbol.split(" ");
                        realEstate.setSymbol(symbols[num]);
                        realEstate.setCompanyName(value);
                    }
                    case 2 -> realEstate.setPrice(value);
                    case 3 -> realEstate.setChange(value);
                    case 4 -> realEstate.setChangePercentage(value);
                    case 5 -> realEstate.setVolume(value);
                    case 6 -> realEstate.setAverageVolume(value);
                    case 7 -> realEstate.setMarketCap(value);
                    case 8 -> {
                        Elements peRatioElements = doc.select("td[aria-label='PE Ratio (TTM)']");
                        String peRatioValue = peRatioElements.text().trim();
                        String[] values = peRatioValue.split(" ");
                        realEstate.setPeRatio(values[num]);
                    }
                    default -> {
                    }
                }
            }
        }
        return realEstates;
    }

    public static List<Energy> parseEnergies(String html) {
        log.info("Converting incoming html into energies......");
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("td[aria-label]");

        List<Energy> energies = new ArrayList<>();
        Energy energy = null;

        for (int e = 0; e < elements.size(); e++) {
            String value = elements.get(e).text().trim();

            if (e % 10 == 0) {
                energy = new Energy();
                energies.add(energy);
            } else {
                int num = energies.size() - 1;
                switch (e % 10) {
                    case 1 -> {
                        Elements symbolElement = doc.select("td[aria-label=Symbol] a[data-test=quoteLink]");
                        String symbol = symbolElement.text().trim();
                        String[] symbols = symbol.split(" ");
                        energy.setSymbol(symbols[num]);
                        energy.setCompanyName(value);
                    }
                    case 2 -> energy.setPrice(value);
                    case 3 -> energy.setChange(value);
                    case 4 -> energy.setChangePercentage(value);
                    case 5 -> energy.setVolume(value);
                    case 6 -> energy.setAverageVolume(value);
                    case 7 -> energy.setMarketCap(value);
                    case 8 -> {
                        Elements peRatioElements = doc.select("td[aria-label='PE Ratio (TTM)']");
                        String peRatioValue = peRatioElements.text().trim();
                        String[] values = peRatioValue.split(" ");
                        energy.setPeRatio(values[num]);
                    }
                    default -> {
                    }
                }
            }
        }
        return energies;
    }

    public static List<Technology> parseTechnologies(String html) {
        log.info("Converting incoming html into technologies......");
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("td[aria-label]");

        List<Technology> technologies = new ArrayList<>();
        Technology technology = null;

        for (int e = 0; e < elements.size(); e++) {
            String value = elements.get(e).text().trim();

            if (e % 10 == 0) {
                technology = new Technology();
                technologies.add(technology);
            } else {
                int num = technologies.size() - 1;
                switch (e % 10) {
                    case 1 -> {
                        Elements symbolElement = doc.select("td[aria-label=Symbol] a[data-test=quoteLink]");
                        String symbol = symbolElement.text().trim();
                        String[] symbols = symbol.split(" ");
                        technology.setSymbol(symbols[num]);
                        technology.setCompanyName(value);
                    }
                    case 2 -> technology.setPrice(value);
                    case 3 -> technology.setChange(value);
                    case 4 -> technology.setChangePercentage(value);
                    case 5 -> technology.setVolume(value);
                    case 6 -> technology.setAverageVolume(value);
                    case 7 -> technology.setMarketCap(value);
                    case 8 -> {
                        Elements peRatioElements = doc.select("td[aria-label='PE Ratio (TTM)']");
                        String peRatioValue = peRatioElements.text().trim();
                        String[] values = peRatioValue.split(" ");
                        technology.setPeRatio(values[num]);
                    }
                    default -> {
                    }
                }
            }
        }
        return technologies;
    }

    public static List<Finance> parseFinances(String html) {
        log.info("Converting incoming html into finances......");
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("td[aria-label]");

        List<Finance> finances = new ArrayList<>();
        Finance finance = null;

        for (int e = 0; e < elements.size(); e++) {
            String value = elements.get(e).text().trim();

            if (e % 10 == 0) {
                finance = new Finance();
                finances.add(finance);
            } else {
                int num = finances.size() - 1;
                switch (e % 10) {
                    case 1 -> {
                        Elements symbolElement = doc.select("td[aria-label=Symbol] a[data-test=quoteLink]");
                        String symbol = symbolElement.text().trim();
                        String[] symbols = symbol.split(" ");
                        finance.setSymbol(symbols[num]);
                        finance.setCompanyName(value);
                    }
                    case 2 -> finance.setPrice(value);
                    case 3 -> finance.setChange(value);
                    case 4 -> finance.setChangePercentage(value);
                    case 5 -> finance.setVolume(value);
                    case 6 -> finance.setAverageVolume(value);
                    case 7 -> finance.setMarketCap(value);
                    case 8 -> {
                        Elements peRatioElements = doc.select("td[aria-label='PE Ratio (TTM)']");
                        String peRatioValue = peRatioElements.text().trim();
                        String[] values = peRatioValue.split(" ");
                        finance.setPeRatio(values[num]);
                    }
                    default -> {
                    }
                }
            }
        }
        return finances;
    }

    public static List<Crypto> parseCrypto(String html) {
        log.info("Converting incoming html into crypto......");
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("tr.simpTblRow");

        List<Crypto> cryptoList = new ArrayList<>();

        for (Element e : elements) {
            Crypto crypto = Crypto.builder()
                    .symbol(e.select("td[aria-label=Symbol] a").text())
                    .name(e.select("td[aria-label=Name]").text())
                    .price(e.select("td[aria-label='Price (Intraday)']").text())
                    .change(e.select("td[aria-label=Change] span").text())
                    .changePercentage(e.select("td[aria-label='% Change'] span").text())
                    .marketCap(e.select("td[aria-label='Market Cap']").text())
                    .volumeInCurrencySince00UTC(e.select("td[aria-label='Volume in Currency (Since 0:00 UTC)']").text())
                    .volumeInCurrency24Hr(e.select("td[aria-label='Volume in Currency (24Hr)']").text())
                    .totalVolumeAllCurrencies24Hr(e.select("td[aria-label='Total Volume All Currencies (24Hr)']").text())
                    .circulatingSupply(e.select("td[aria-label='Circulating Supply']").text())
                    .build();

            cryptoList.add(crypto);
        }
        return cryptoList;
    }
}
