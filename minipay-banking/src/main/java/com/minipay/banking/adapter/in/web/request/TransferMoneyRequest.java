package com.minipay.banking.adapter.in.web.request;

import java.math.BigDecimal;

public record TransferMoneyRequest(
        String srcBankName,
        String srcAccountNumber,
        String destBankName,
        String destAccountNumber,
        BigDecimal amount
) {
}
