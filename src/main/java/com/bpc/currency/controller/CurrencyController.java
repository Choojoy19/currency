package com.bpc.currency.controller;

import com.bpc.currency.datevalidator.DateValidator;
import com.bpc.currency.models.Rate;
import com.bpc.currency.models.RateWithChange;
import com.bpc.currency.service.impl.CurrencyServiceImpl;
import com.bpc.currency.service.impl.DBServiceImpl;
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
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyServiceImpl serviceCurrency;
    private final DBServiceImpl serviceDB;

    public CurrencyController(CurrencyServiceImpl serviceCurrency, DBServiceImpl serviceDB) {
        this.serviceCurrency = serviceCurrency;
        this.serviceDB = serviceDB;
    }

    @GetMapping ("/{date}")
    public ResponseEntity<List<Rate>> saveRatesByDate (@PathVariable(value="date") String date) {
        LocalDate dateCorrect = DateValidator.dateParser(date);
        if (DateValidator.isDateNotEarlierToday(dateCorrect)) {
            try {
                List<Rate> rates = serviceCurrency.getRatesFromSource(dateCorrect);
                serviceDB.saveRatesToDB(rates);
                return new ResponseEntity<>(rates, HttpStatus.OK);
            } catch (NullPointerException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else { return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
    }

    @GetMapping ("/{cur_id}/{date}")
    public ResponseEntity <?> showRateByDateAndId (@PathVariable (value="date") String date, @PathVariable(value="cur_id") int id) {
        LocalDate dateCorrect = DateValidator.dateParser(date);
        if (DateValidator.isDateNotEarlierToday(dateCorrect)){
            try {
                RateWithChange rateWithChange = serviceCurrency.getRateWithChange((dateCorrect), id);
                HttpHeaders responseHeader = new HttpHeaders();
                responseHeader.set("crc32code", serviceCurrency.calculateCRC32(rateWithChange));
                return ResponseEntity.ok().headers(responseHeader).body(rateWithChange);
            } catch (NullPointerException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else { return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}

    }


}
