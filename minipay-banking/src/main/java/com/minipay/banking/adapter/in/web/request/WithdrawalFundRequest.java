package com.minipay.banking.adapter.in.web.request;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawalFundRequest(
        UUID bankAccountId,
        String bankName,
        String bankAccountNumber,
        BigDecimal amount
) {
}
