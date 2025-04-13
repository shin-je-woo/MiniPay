package com.minipay.banking.adapter.in.kafka;

import java.math.BigDecimal;
import java.util.UUID;

public record MemberMoneyEventPayload(
        UUID memberMoneyId,
        UUID bankAccountId,
        UUID moneyHistoryId,
        BigDecimal amount
) {
}
