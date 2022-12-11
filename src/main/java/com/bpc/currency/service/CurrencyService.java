package com.bpc.currency.service;

import com.bpc.currency.models.Rate;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyService {

    void saveRatesToDB (List<Rate> list);
    List<Rate> getRatesFromSource (LocalDate date);
    Rate getRatesFromSource (LocalDate date, int currencyCode);


}
