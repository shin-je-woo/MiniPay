package com.minipay.money.application.port.out;

import java.util.UUID;

public record BankAccountInfo(
        UUID bankAccountId,
        UUID membershipId
) {
}
