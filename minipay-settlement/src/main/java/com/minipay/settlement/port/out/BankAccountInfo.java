package com.minipay.settlement.port.out;

import java.util.UUID;

public record BankAccountInfo(
        UUID bankAccountId,
        String bankName,
        String accountNumber
) {
}
