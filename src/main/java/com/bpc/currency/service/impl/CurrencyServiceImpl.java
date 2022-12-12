package com.bpc.currency.service.impl;

import com.bpc.currency.models.Rate;
import com.bpc.currency.models.RateRespWithChange;
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
import java.util.zip.CRC32C;

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


    @Override
    public RateRespWithChange getRateWithChange(Rate rateCurrent, LocalDate dateToday, int currencyCode) {
        ResponseEntity<Rate> responseEntityBefore = restTemplate.getForEntity(BASE_URL + "/" + currencyCode + "?ondate=" + dateToday.minusDays(1), Rate.class);
        Rate rateBefore = (Rate) responseEntityBefore.getBody();
        if (rateCurrent.Cur_OfficialRate<rateBefore.Cur_OfficialRate){
            return new RateRespWithChange(rateCurrent, "decreased");
        } else if (rateCurrent.Cur_OfficialRate>rateBefore.Cur_OfficialRate){
            return new RateRespWithChange(rateCurrent, "increased");
        }else {
            return new RateRespWithChange(rateCurrent, "not change");
        }
    }

    @Override
    public String calculateCRC32(Rate rate) {
        CRC32C coder = new CRC32C();
        coder.update(rate.toString().getBytes());
        return String.valueOf(coder.getValue());
    }

    @Override
    public String calculateCRC32(RateRespWithChange rate) {
        CRC32C coder = new CRC32C();
        coder.update(rate.toString().getBytes());
        return String.valueOf(coder.getValue());
    }


}
