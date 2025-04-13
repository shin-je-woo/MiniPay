package com.minipay.money.adapter.in.kafka;

import java.math.BigDecimal;
import java.util.UUID;

public record FundTransactionEventPayload(
        UUID fundTransactionId,
        UUID bankAccountId,
        UUID moneyHistoryId,
        BigDecimal amount
) {
}
