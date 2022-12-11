package com.bpc.currency.service.impl;

import com.bpc.currency.models.Rate;
import com.bpc.currency.repository.CurrencyRepo;
import com.bpc.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final String BASE_URL = "https://www.nbrb.by/api/exrates/rates";
    @Autowired
    private final RestTemplate restTemplate;
    private final CurrencyRepo currencyRepo;

    public CurrencyServiceImpl(RestTemplate restTemplate, CurrencyRepo currencyRepo) {
        this.restTemplate = restTemplate;
        this.currencyRepo = currencyRepo;
    }


    @Override
    public void saveRatesToDB(List<Rate> list) {
        currencyRepo.saveAll(list);

    }

    @Override
    public List<Rate> getRatesFromSource(LocalDate  date) {
        ResponseEntity<Rate[]> responseEntity = restTemplate.getForEntity(BASE_URL+"?ondate="+date+"&periodicity=0", Rate[].class);
        Rate[] rateArray = responseEntity.getBody();
        return  Arrays.stream(rateArray).collect(Collectors.toList());
    }


    @Override
    public Rate getRatesFromSource(LocalDate date, int currencyCode){
        ResponseEntity<Rate> responseEntity = restTemplate.getForEntity(BASE_URL+"/"+currencyCode+"?ondate="+date, Rate.class);
        return (Rate) responseEntity.getBody();

    }
}
