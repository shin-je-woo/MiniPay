package com.minipay.banking.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawalFundCreatedEvent(
        UUID fundTransactionId,
        UUID bankAccountId,
        String withdrawalBankName,
        String withdrawalAccountNumber,
        String minipayBankAccount,
        String minipayBankAccountName,
        String minipayBankAccountNumber,
        String fundType,
        BigDecimal amount,
        String status
) {
}
