package com.bpc.currency.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name="rates")
public class Rate  {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int ID;
    @JsonProperty("Cur_ID")
    private int Cur_ID;
    @JsonProperty("Date")
    private LocalDate Date;
    @JsonProperty("Cur_Abbreviation")
    private String Cur_Abbreviation;
    @JsonProperty("Cur_Scale")
    private int Cur_Scale;
    @JsonProperty("Cur_Name")
    private String Cur_Name;
    @JsonProperty("Cur_OfficialRate")
    public double Cur_OfficialRate;

    @Override
    public String toString() {
        return "Rate{" +
                "Cur_ID=" + Cur_ID +
                ", Date=" + Date +
                ", Cur_Abbreviation='" + Cur_Abbreviation + '\'' +
                ", Cur_Scale=" + Cur_Scale +
                ", Cur_Name='" + Cur_Name + '\'' +
                ", Cur_OfficialRate=" + Cur_OfficialRate +
                '}';
    }
}
