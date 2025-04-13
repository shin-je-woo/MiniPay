package com.minipay.money.adapter.in.kafka;

import java.util.UUID;

public record BankAccountEventPayload(
        UUID bankAccountId,
        UUID membershipId,
        String bankName,
        String accountNumber
) {
}
