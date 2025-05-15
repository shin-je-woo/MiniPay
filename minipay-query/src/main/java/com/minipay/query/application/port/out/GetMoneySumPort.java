package com.minipay.query.application.port.out;

import com.minipay.query.domain.MoneySumByRegion;

import java.time.LocalDate;

public interface GetMoneySumPort {
    MoneySumByRegion getMoneySumByAddress(String address);
    MoneySumByRegion getDailyMoneySumByAddress(String address, LocalDate date);
}
