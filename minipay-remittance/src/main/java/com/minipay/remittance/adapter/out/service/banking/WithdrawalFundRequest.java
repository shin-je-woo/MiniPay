package com.minipay.remittance.adapter.out.service.banking;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record WithdrawalFundRequest(
        UUID bankAccountId,
        String bankName,
        String bankAccountNumber,
        BigDecimal amount
) {
}
