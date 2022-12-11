package com.bpc.currency.repository;

import com.bpc.currency.models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepo extends JpaRepository <Rate, Integer> {
}
