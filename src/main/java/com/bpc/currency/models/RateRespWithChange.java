package com.bpc.currency.models;


public class RateRespWithChange {
    private Rate rate;
    private String rateChange;

    public RateRespWithChange (Rate rate, String rateChange){
        this.rate =rate;
        this.rateChange=rateChange;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public String getRateChange() {
        return rateChange;
    }

    public void setRateChange(String rateChange) {
        this.rateChange = rateChange;
    }

    @Override
    public String toString() {
        return "RateRespWithChange{" +
                "rate=" + rate +
                ", rateChange='" + rateChange + '\'' +
                '}';
    }
}
