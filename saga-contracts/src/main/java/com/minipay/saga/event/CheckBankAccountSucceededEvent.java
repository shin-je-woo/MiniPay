package com.minipay.saga.event;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckBankAccountSucceededEvent(
        UUID checkBankAccountId,
        UUID rechargeRequestId,
        UUID bankAccountId,
        UUID memberMoneyId,
        UUID moneyHistoryId,
        BigDecimal amount,
        String bankName,
        String accountNumber
) {
}
