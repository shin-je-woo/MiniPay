package com.minipay.banking.application.port.out;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record FirmBankingRequest(
        String srcBankName,
        String srcAccountNumber,
        String destBankName,
        String destAccountNumber,
        BigDecimal amount
) {
}
