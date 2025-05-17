package com.minipay.query.application.port.out;

import com.minipay.query.domain.MoneySumByMembership;

import java.util.List;

public record MoneySumByMembershipsResult(
        List<MoneySumByMembership> moneySumByMemberships
) {
}
