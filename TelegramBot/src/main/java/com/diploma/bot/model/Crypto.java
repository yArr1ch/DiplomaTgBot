package com.diploma.bot.model;

import lombok.*;

@Getter
@Builder
public class Crypto {
    private String symbol;
    private String name;
    private String price;
    private String change;
    private String changePercentage;
    private String marketCap;
    private String volumeInCurrencySince00UTC;
    private String volumeInCurrency24Hr;
    private String totalVolumeAllCurrencies24Hr;
    private String circulatingSupply;
}
