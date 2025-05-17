package com.minipay.query.application.port.out;

import com.minipay.query.domain.MoneySumByMembership;
import com.minipay.query.domain.MoneySumByRegion;

import java.time.LocalDate;
import java.util.List;

public interface GetMoneySumPort {
    MoneySumByRegion getMoneySumByAddress(String address);
    MoneySumByRegion getDailyMoneySumByAddress(String address, LocalDate date);
    List<MoneySumByMembership> getTopMoneySumByMembership(String address, int fetchSize);
}
