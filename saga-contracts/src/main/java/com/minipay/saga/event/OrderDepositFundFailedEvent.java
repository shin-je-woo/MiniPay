package com.minipay.saga.event;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderDepositFundFailedEvent(
        UUID orderDepositFundId,
        UUID bankAccountId,
        UUID checkBankAccountId,
        UUID memberMoneyId,
        UUID moneyHistoryId,
        BigDecimal amount,
        String bankName,
        String accountNumber
) {
}
