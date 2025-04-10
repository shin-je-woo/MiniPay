package com.minipay.saga.event;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckBankAccountFailedEvent(
        UUID checkBankAccountId,
        UUID rechargeRequestId,
        UUID bankAccountId,
        UUID memberMoneyId,
        UUID moneyHistoryId,
        BigDecimal amount
) {
}
