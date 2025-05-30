package com.minipay.settlement.adapter.out.service.banking;

import java.util.UUID;

public record BankAccountResponse(
        UUID bankAccountId,
        UUID membershipId,
        String linkedBankName,
        String linkedAccountNumber,
        String linkedStatus
) {
}
