package com.diploma.bot.util;

import com.diploma.bot.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class ImageGenerator {

    private static int x = 10;
    private static int y = 20;

    //use func
    public static byte[] generateImageFromStocks(List<Stock> stocks) {
        return createGeneralImage(1100, 2100, stocks);
    }

    //use func
    public static byte[] generateImageFromRealEstate(List<RealEstate> realEstates) {
        return createGeneralImage(1080, 2060, realEstates);
    }

    //use func
    public static byte[] generateImageFromEnergies(List<Energy> energies) {
        return createGeneralImage(1080, 2060, energies);
    }

    //use func
    public static byte[] generateImageFromTechnologies(List<Technology> technologies) {
        return createGeneralImage(1080, 2100, technologies);
    }

    //use func
    public static byte[] generateImageFromFinances(List<Finance> finances) {
        return createGeneralImage(1080, 2100, finances);
    }

    public static byte[] generateImageFromFutures(List<Futures> futures) {
        Pair<Graphics2D, BufferedImage> imagePair = utilizeImageGeneration(740, 780);
        Graphics2D g2d = imagePair.getLeft();
        BufferedImage image = imagePair.getRight();

        int[] columnWidths = {70, 200, 80, 90, 80, 120, 80};

        String[] columnNames = {"Symbol", "Name", "Last Price", "Market Time", "Change", "Change Percentage", "Volume"};

        for (int i = 0; i < columnNames.length; i++) {

            g2d.drawString(columnNames[i], x, y);
            x += columnWidths[i];
        }

        y += 20;
        for (Futures future : futures) {
            x = 10;
            g2d.drawString(future.getSymbol(), x, y);
            x += columnWidths[0];
            g2d.drawString(future.getName(), x, y);
            x += columnWidths[1];
            g2d.drawString(future.getLastPrice(), x, y);
            x += columnWidths[2];
            g2d.drawString(future.getMarketTime(), x, y);
            x += columnWidths[3];
            g2d.drawString(future.getChange(), x, y);
            x += columnWidths[4];
            g2d.drawString(future.getChangePercentage(), x, y);
            x += columnWidths[5];
            g2d.drawString(future.getVolume(), x, y);
            x += columnWidths[6];
            y += 20;
        }

        g2d.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        resetSize();
        return outputStream.toByteArray();
    }

    public static byte[] generateImageFromCurrencies(List<Currency> currencies) {
        Pair<Graphics2D, BufferedImage> imagePair = utilizeImageGeneration(460, 520);
        Graphics2D g2d = imagePair.getLeft();
        BufferedImage image = imagePair.getRight();

        int[] columnWidths = {80, 80, 80, 80, 150};

        String[] columnNames = {"Symbol", "Name", "Last Price", "Change", "Change Percentage"};

        for (int i = 0; i < columnNames.length; i++) {

            g2d.drawString(columnNames[i], x, y);
            x += columnWidths[i];
        }

        y += 20;
        for (Currency currency : currencies) {
            x = 10;
            g2d.drawString(currency.getSymbol(), x, y);
            x += columnWidths[0];
            g2d.drawString(currency.getName(), x, y);
            x += columnWidths[1];
            g2d.drawString(currency.getLastPrice(), x, y);
            x += columnWidths[2];
            g2d.drawString(currency.getChange(), x, y);
            x += columnWidths[3];
            g2d.drawString(currency.getChangePercentage(), x, y);
            x += columnWidths[4];
            y += 20;
        }

        g2d.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        resetSize();
        return outputStream.toByteArray();
    }

    public static byte[] generateImageFromCrypto(List<Crypto> cryptoList) {
        Pair<Graphics2D, BufferedImage> imagePair = utilizeImageGeneration(1350, 2050);
        Graphics2D g2d = imagePair.getLeft();
        BufferedImage image = imagePair.getRight();

        int[] columnWidths = {100, 160, 85, 85, 120, 100, 210, 170, 200, 100};

        String[] columnNames = {"Symbol", "Name", "Price", "Change", "Change Percentage", "Market Cap",
                "Volume in Currency(Since 0:00 UTC)", "Volume in Currency(24Hr)", "Total Volume All Currencies(24Hr)",
                "Circulating Supply"};

        for (int i = 0; i < columnNames.length; i++) {

            g2d.drawString(columnNames[i], x, y);
            x += columnWidths[i];
        }

        y += 20;
        for (Crypto crypto : cryptoList) {
            x = 10;
            g2d.drawString(crypto.getSymbol(), x, y);
            x += columnWidths[0];
            g2d.drawString(crypto.getName(), x, y);
            x += columnWidths[1];
            g2d.drawString(crypto.getPrice(), x, y);
            x += columnWidths[2];
            g2d.drawString(crypto.getChange(), x, y);
            x += columnWidths[3];
            g2d.drawString(crypto.getChangePercentage(), x, y);
            x += columnWidths[4];
            g2d.drawString(crypto.getMarketCap(), x, y);
            x += columnWidths[5];
            g2d.drawString(crypto.getVolumeInCurrencySince00UTC(), x, y);
            x += columnWidths[6];
            g2d.drawString(crypto.getVolumeInCurrency24Hr(), x, y);
            x += columnWidths[7];
            g2d.drawString(crypto.getTotalVolumeAllCurrencies24Hr(), x, y);
            x += columnWidths[8];
            g2d.drawString(crypto.getCirculatingSupply(), x, y);
            x += columnWidths[9];
            y += 20;
        }

        g2d.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        resetSize();
        return outputStream.toByteArray();
    }

    private static Pair<Graphics2D, BufferedImage> utilizeImageGeneration(int imageWidth, int imageLength) {

        BufferedImage image = new BufferedImage(imageWidth, imageLength, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imageWidth, imageLength);

        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.setColor(Color.BLACK);

        return Pair.of(g2d, image);
    }

    private static byte[] createGeneralImage(int imageWidth, int imageLength, List<? extends SectorModel> sectorModels) {
        Pair<Graphics2D, BufferedImage> imagePair = utilizeImageGeneration(imageWidth, imageLength);
        Graphics2D g2d = imagePair.getLeft();
        BufferedImage image = imagePair.getRight();

        int[] columnWidths = {80, 250, 80, 80, 150, 100, 120, 120, 100};

        String[] columnNames = {"Symbol", "Company Name", "Price", "Change", "Change Percentage",
                "Volume", "Average Volume", "Market Cap", "P/E Ratio"};

        for (int i = 0; i < columnNames.length; i++) {

            g2d.drawString(columnNames[i], x, y);
            x += columnWidths[i];
        }

        int count = 0;
        int index = -1;
        y += 20;
        
        for (SectorModel sectorModel : sectorModels) {
            String compName = sectorModel.getCompanyName();
            x = 10;
            g2d.drawString(sectorModel.getSymbol(), x, y);
            x += columnWidths[0];

            if (compName.length() > 40) {
                for (int i = 0; i < compName.length(); i++) {
                    if (compName.charAt(i) == ' ') {
                        count++;
                        if (count == 3) {
                            index = i;
                            break;
                        }
                    }
                }
                if (index != -1) {
                    String part1 = compName.substring(0, index);
                    String part2 = compName.substring(index + 1);
                    g2d.drawString(part1, x, y);
                    y += 20;
                    g2d.drawString(part2, x, y);
                }
            } else {
                g2d.drawString(sectorModel.getCompanyName(), x, y);
            }

            x += columnWidths[1];
            g2d.drawString(sectorModel.getPrice(), x, y);
            x += columnWidths[2];
            g2d.drawString(sectorModel.getChange(), x, y);
            x += columnWidths[3];
            g2d.drawString(sectorModel.getChangePercentage(), x, y);
            x += columnWidths[4];
            g2d.drawString(sectorModel.getVolume(), x, y);
            x += columnWidths[5];
            g2d.drawString(sectorModel.getAverageVolume(), x, y);
            x += columnWidths[6];
            g2d.drawString(sectorModel.getMarketCap(), x, y);
            x += columnWidths[7];
            g2d.drawString(sectorModel.getPeRatio(), x, y);
            y += 20;
        }

        g2d.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        resetSize();
        return outputStream.toByteArray();
    }

    public static void resetSize() {
        y = 20;
        x = 10;
    }
}
