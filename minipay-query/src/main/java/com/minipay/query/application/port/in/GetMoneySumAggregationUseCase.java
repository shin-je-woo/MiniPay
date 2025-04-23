package com.minipay.query.application.port.in;

import java.math.BigDecimal;

public interface GetMoneySumAggregationUseCase {
    BigDecimal getMoneySumByAddress(GetMoneySumByAddressQuery query);
}
