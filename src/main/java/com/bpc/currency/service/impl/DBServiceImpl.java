package com.bpc.currency.service.impl;

import com.bpc.currency.models.Rate;
import com.bpc.currency.repository.CurrencyRepo;
import com.bpc.currency.service.DBService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBServiceImpl implements DBService {

    private final CurrencyRepo currencyRepo;

    public DBServiceImpl(CurrencyRepo currencyRepo) {
        this.currencyRepo = currencyRepo;
    }


    @Override
    public void saveRatesToDB(List<Rate> list) {
        currencyRepo.saveAll(list);
    }
}
