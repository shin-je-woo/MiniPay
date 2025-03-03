package com.minipay.money.adapter.in.kafka;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.util.UUID;

public record MinipayFundEventPayload(
        UUID minipayFundId,
        UUID bankAccountId,
        UUID moneyHistoryId,
        BigDecimal amount
) {
}
