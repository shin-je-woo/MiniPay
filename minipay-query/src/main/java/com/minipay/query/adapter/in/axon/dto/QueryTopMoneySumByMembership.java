package com.minipay.query.adapter.in.axon.dto;

public record QueryTopMoneySumByMembership(
        String address,
        int fetchSize
) {
}
