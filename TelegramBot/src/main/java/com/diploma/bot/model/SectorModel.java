package com.diploma.bot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectorModel {
    private String symbol;
    private String companyName;
    private String price;
    private String change;
    private String changePercentage;
    private String volume;
    private String averageVolume;
    private String marketCap;
    private String peRatio;
}
