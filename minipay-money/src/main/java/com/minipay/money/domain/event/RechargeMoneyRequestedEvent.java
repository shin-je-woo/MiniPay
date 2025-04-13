package com.minipay.money.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

public record RechargeMoneyRequestedEvent(
        UUID rechargeRequestId,
        UUID memberMoneyId,
        UUID moneyHistoryId,
        UUID membershipId,
        UUID bankAccountId,
        BigDecimal amount
) {
}
