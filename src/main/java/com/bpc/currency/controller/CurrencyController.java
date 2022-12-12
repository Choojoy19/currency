package com.bpc.currency.controller;


import com.bpc.currency.models.Rate;
import com.bpc.currency.models.RateRespWithChange;
import com.bpc.currency.service.impl.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/app")
public class CurrencyController {

    private final CurrencyServiceImpl service;

    @Autowired
    public CurrencyController (CurrencyServiceImpl service){
        this.service = service;
    }

    @GetMapping ("/{date}")
    public ResponseEntity<List<Rate>> showRatesByDate (@PathVariable(value="date") LocalDate  date) {
        List<Rate> rates= service.getRatesFromSource(date);
        service.saveRatesToDB(rates);
        return new ResponseEntity<>(rates, HttpStatus.OK);
    }

    @GetMapping ("/{cur_id}/{date}")
    public ResponseEntity <?> showRateByDateAndId (@PathVariable (value="date") LocalDate date, @PathVariable(value="cur_id") int id) {
        Rate rateCurrent = service.getRatesFromSource(date, id); // без доп задания, помещаем его в ResponseEntity
        RateRespWithChange rateRespWithChange= service.getRateWithChange(rateCurrent, date, id); // +информация об изменении курса
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("crc32code",service.calculateCRC32(rateRespWithChange));
        return ResponseEntity.ok().headers(responseHeader).body(rateRespWithChange);
    }


}
