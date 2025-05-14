package com.minipay.query.application.port.out;

import com.minipay.query.domain.MoneySumByRegion;

public interface GetMoneySumPort {
    MoneySumByRegion getMoneySumByAddress(String address);
}
