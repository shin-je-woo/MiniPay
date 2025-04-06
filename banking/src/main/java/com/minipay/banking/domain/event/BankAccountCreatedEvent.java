package com.minipay.banking.domain.event;

import java.util.UUID;

public record BankAccountCreatedEvent(
    UUID bankAccountId,
    UUID membershipId,
    String bankName,
    String accountNumber,
    String linkedStatus
) {
}
