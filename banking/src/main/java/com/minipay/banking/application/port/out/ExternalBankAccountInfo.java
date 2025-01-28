package com.minipay.banking.application.port.out;

public record ExternalBankAccountInfo(
        String bankName,
        String accountNumber,
        boolean isValid
) {
}

