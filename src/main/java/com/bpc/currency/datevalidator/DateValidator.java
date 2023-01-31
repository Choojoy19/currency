package com.bpc.currency.datevalidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator {

    static LocalDate today = LocalDate.now();
    static LocalDate dateCorrect;

    public static LocalDate dateParser (String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            dateCorrect = LocalDate.parse(date, formatter);
        } catch (Exception e){
        e.printStackTrace();
        }
        return dateCorrect;
    }

    public static boolean isDateNotEarlierToday (LocalDate date){
        return dateCorrect.isBefore(today);


    }
}
