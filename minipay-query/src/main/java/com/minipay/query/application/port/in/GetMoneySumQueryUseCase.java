package com.minipay.query.application.port.in;

import com.minipay.query.domain.MoneySumByRegion;

public interface GetMoneySumQueryUseCase {
    MoneySumByRegion getMoneySumByAddress(GetMoneySumByAddressQuery query);
    MoneySumByRegion getDailyMoneySumByAddress(GetDailyMoneySumByAddressQuery query);
}
