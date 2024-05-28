package com.diploma.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    //private final NewsRepository newsRepository;

    private final Map<String, String> map = new HashMap<>();
    private static final String LINK_REGEX = "href=\"(https?://.*?)\"";
    private static final String TITLE_REGEX = "title=\"(.*?)\"";
    private static final String STOCK_MARKET_NEWS_URL = "https://finance.yahoo.com/topic/stock-market-news/";
    private static final String EARNINGS_NEWS_URL = "https://finance.yahoo.com/topic/earnings/";
    private static final String POLITICS_NEWS_URL = "https://finance.yahoo.com/live/politics/";
    private static final String ECONOMIC_NEWS_URL = "https://finance.yahoo.com/topic/economic-news/";
    private static final String PERSONAL_FINANCE_NEWS_URL = "https://finance.yahoo.com/topic/personal-finance-news/";
    private static final String CRYPTO_NEWS_URL = "https://finance.yahoo.com/topic/crypto/";
    private static final String LATEST_NEWS_URL = "https://finance.yahoo.com/news/";


    public void getLatestYahooNews() throws IOException {
        Document doc = Jsoup.connect(LATEST_NEWS_URL).get();
        Elements divs = doc.select("div.content");

        int limit = 5;
        int count = 0;
        for (Element div : divs) {
            String html = div.html();

            Matcher matcherLink = Pattern.compile(LINK_REGEX).matcher(html);
            Matcher matcherTitle = Pattern.compile(TITLE_REGEX).matcher(html);

            while (matcherLink.find() && matcherTitle.find() && count < limit) {
                String url = matcherLink.group(1);
                String title = matcherTitle.group(1);
                log.info(url);
                log.info(title);
                count++;

                if (!map.containsKey(url)) {
                    map.put(url, title);
                }
            }
        }
    }

    public void getStockMarketNews() throws IOException {
        newsExtraction(STOCK_MARKET_NEWS_URL);
    }

    public void getEarningsNews() throws IOException {
        newsExtraction(EARNINGS_NEWS_URL);
    }

    public void getPoliticsNews() throws IOException {
        newsExtraction(POLITICS_NEWS_URL);
    }

    public void getEconomicNews() throws IOException {
        newsExtraction(ECONOMIC_NEWS_URL);
    }

    public void getPersonalFinanceNews() throws IOException {
        newsExtraction(PERSONAL_FINANCE_NEWS_URL);
    }

    public void getCryptoNews() throws IOException {
        newsExtraction(CRYPTO_NEWS_URL);
    }

    public Map<String, String> runAllNews() throws IOException {
        getLatestYahooNews();
        getStockMarketNews();
        getEconomicNews();
        getEarningsNews();
        getPoliticsNews();
        getCryptoNews();
        getPersonalFinanceNews();
        return map;
    }


    private void checkNewsForDuplication() {
        //List<NewsItem> newsList = newsRepository.findAll();

        //compare it and use in each method upper
    }

    private void newsExtraction(String newsUrl) throws IOException {
        Document doc = Jsoup.connect(newsUrl).get();
        Elements elements = doc.select("h3[class~=(Mb\\(5px\\))] > a");

        // it takes only 3 latest
        for (Element element : elements) {
            String url = element.attr("href");
            String title = element.text();

            log.info(title);
            log.info(url);

            if (!map.containsKey(url)) {
                map.put(url, title);
            }
        }
    }
}