package com.bpc.currency.service.impl;

import com.bpc.currency.models.Rate;
import com.bpc.currency.models.RateWithChange;
import com.bpc.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.CRC32C;

@Service
@PropertySource("classpath:application.properties")
public class CurrencyServiceImpl implements CurrencyService {

    @Value("${BASE_URL}")
    private String BASE_URL;
    private final RestTemplate restTemplate;

    public CurrencyServiceImpl(RestTemplate restTemplate ) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Rate> getRatesFromSource(LocalDate  date) throws NullPointerException {
        ResponseEntity<Rate[]> responseEntity = restTemplate.getForEntity(BASE_URL+"?ondate="+date+"&periodicity=0", Rate[].class);
        Rate[] rateArray = responseEntity.getBody();
        return  Arrays.stream(rateArray).collect(Collectors.toList());
    }

    @Override
    public RateWithChange getRateWithChange(LocalDate dateToday, int currencyCode) throws NullPointerException{
            ResponseEntity<Rate> responseEntityCurrent = restTemplate.getForEntity(BASE_URL + "/" + currencyCode + "?ondate=" + dateToday, Rate.class);
            ResponseEntity<Rate> responseEntityBefore = restTemplate.getForEntity(BASE_URL + "/" + currencyCode + "?ondate=" + dateToday.minusDays(1), Rate.class);
            Rate rateCurrent = responseEntityCurrent.getBody();
            Rate rateBefore = responseEntityBefore.getBody();
            if (rateCurrent.Cur_OfficialRate < rateBefore.Cur_OfficialRate) {
                return new RateWithChange(rateCurrent, "decreased");
            } else if (rateCurrent.Cur_OfficialRate > rateBefore.Cur_OfficialRate) {
                return new RateWithChange(rateCurrent, "increased");
            } else {
                return new RateWithChange(rateCurrent, "not change");
            }
    }

    @Override
    public String calculateCRC32(RateWithChange rate) {
        CRC32C coder = new CRC32C();
        coder.update(rate.toString().getBytes());
        return String.valueOf(coder.getValue());
    }

}
