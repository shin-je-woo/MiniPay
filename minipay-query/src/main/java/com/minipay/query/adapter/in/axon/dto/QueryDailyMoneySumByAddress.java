package com.minipay.query.adapter.in.axon.dto;

import java.time.LocalDate;

public record QueryDailyMoneySumByAddress(
        String address,
        LocalDate date
) {
}
