package com.minipay.saga.event;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositFundCreatedEvent(
        UUID orderDepositFundId,
        UUID fundTransactionId,
        UUID bankAccountId,
        UUID checkBankAccountId,
        UUID memberMoneyId,
        UUID moneyHistoryId,
        BigDecimal amount,
        String bankName,
        String accountNumber,
        String fundType,
        String fundTransactionStatus,
        String minipayBankAccount
) {
}
