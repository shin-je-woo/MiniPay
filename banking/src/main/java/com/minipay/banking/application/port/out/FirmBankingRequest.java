package com.minipay.banking.application.port.out;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record FirmBankingRequest(
        String fromBankName,
        String fromBankAccountNumber,
        String toBankName,
        String toBankAccountNumber,
        BigDecimal amount
) {
}
