package com.minipay.query.application.port.in;

import java.math.BigDecimal;

public interface GetMoneySumQueryUseCase {
    BigDecimal getMoneySumByAddress(GetMoneySumByAddressQuery query);
}
