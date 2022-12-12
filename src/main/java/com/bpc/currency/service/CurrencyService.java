package com.bpc.currency.service;

import com.bpc.currency.models.Rate;
import com.bpc.currency.models.RateRespWithChange;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyService {

    void saveRatesToDB (List<Rate> list);
    List<Rate> getRatesFromSource (LocalDate  date);
    Rate getRatesFromSource (LocalDate date, int currencyCode);
    RateRespWithChange getRateWithChange(Rate rate, LocalDate dateCurrent, int currencyCode);
    String calculateCRC32 (Rate rate);
    String calculateCRC32 (RateRespWithChange rate);


}
