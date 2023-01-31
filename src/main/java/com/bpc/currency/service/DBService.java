package com.bpc.currency.service;

import com.bpc.currency.models.Rate;
import java.util.List;


public interface DBService {
    void saveRatesToDB (List<Rate> list);
}
