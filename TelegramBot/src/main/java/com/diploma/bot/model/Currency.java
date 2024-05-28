package com.diploma.bot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency {
    private String symbol;
    private String name;
    private String lastPrice;
    private String change;
    private String changePercentage;
}
