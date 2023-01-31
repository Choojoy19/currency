package com.bpc.currency.service;
import com.bpc.currency.models.Rate;
import com.bpc.currency.models.RateWithChange;
import java.time.LocalDate;
import java.util.List;

public interface CurrencyService {

    List<Rate> getRatesFromSource (LocalDate  date);
    RateWithChange getRateWithChange(LocalDate dateCurrent, int currencyCode);
    String calculateCRC32 (RateWithChange rate);

}
