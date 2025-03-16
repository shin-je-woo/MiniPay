package com.minipay.banking.adapter.in.web.request;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawalMinipayFundRequest(
        UUID bankAccountId,
        String bankName,
        String bankAccountNumber,
        BigDecimal amount
) {
}
