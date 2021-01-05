package com.example.currencyconverter;

public class ExchangeRate {
    private String currencyName;
    private static double rateForOneEuro;
    private String capital;

    public ExchangeRate(){}

    public ExchangeRate(String currencyName, String capital, double rateForOneEuro) {
        this.currencyName = currencyName;
        ExchangeRate.rateForOneEuro = rateForOneEuro;
        this.capital = capital;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCapital() {
        return capital;
    }

    public double getRateForOneEuro() {
        return rateForOneEuro;
    }

    public void setRateForOneEuro(double rateForOneEuro){
        ExchangeRate.rateForOneEuro = rateForOneEuro;
    }
    /**
     * Rounds a raw currency value to 2 decimal places
     * @param value Raw currency value
     * @return A rounded off currency value
     */
    public double roundValue(double value) {
        value = Math.round(value * 100.00)/100.00;
        return value;
    }
}
