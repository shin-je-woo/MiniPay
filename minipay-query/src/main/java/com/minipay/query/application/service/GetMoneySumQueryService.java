package com.minipay.query.application.service;

import com.minipay.common.annotation.Query;
import com.minipay.query.application.port.in.GetMoneySumByAddressQuery;
import com.minipay.query.application.port.in.GetMoneySumQueryUseCase;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Query
@RequiredArgsConstructor
public class GetMoneySumQueryService implements GetMoneySumQueryUseCase {

    @Override
    public BigDecimal getMoneySumByAddress(GetMoneySumByAddressQuery query) {
        // not implemented yet
        return null;
    }
}
