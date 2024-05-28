package com.diploma.bot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Futures {

    private String symbol;
    private String name;
    private String lastPrice;
    private String marketTime;
    private String change;
    private String changePercentage;
    private String volume;
}
