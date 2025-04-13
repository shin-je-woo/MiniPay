package com.minipay.banking.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositFundCreatedEvent(
        UUID fundTransactionId,
        UUID bankAccountId,
        String fundType,
        String status,
        BigDecimal amount,
        String minipayBankAccount,
        String minipayBankAccountName,
        String minipayBankAccountNumber
) {
}
