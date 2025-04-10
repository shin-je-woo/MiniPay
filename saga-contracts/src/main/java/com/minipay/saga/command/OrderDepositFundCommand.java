package com.minipay.saga.command;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderDepositFundCommand(
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
