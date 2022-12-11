package com.bpc.currency.controller;


import com.bpc.currency.models.Rate;
import com.bpc.currency.service.impl.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/")
public class CurrencyController {

    private final CurrencyServiceImpl service;

    @Autowired
    public CurrencyController (CurrencyServiceImpl service){
        this.service = service;
    }

    @GetMapping ("/app/{date}")
    public ResponseEntity<List<Rate>> showRatesByDate (@PathVariable(value="date") LocalDate  date) {
        List<Rate> rates= service.getRatesFromSource(date);
        service.saveRatesToDB(rates);
        return new ResponseEntity<>(rates, HttpStatus.OK);
    }

    @GetMapping ("/app/{cur_id}/{date}")
    public ResponseEntity <Rate> showRateByDateAndId (@PathVariable (value="date") LocalDate date, @PathVariable(value="cur_id") int id) {

        return new ResponseEntity<>(service.getRatesFromSource(date, id), HttpStatus.OK);
    }


}
