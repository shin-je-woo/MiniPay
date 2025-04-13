package com.minipay.banking.adapter.in.web.request;

import java.util.UUID;

public record RegisterBankAccountRequest(
        UUID membershipId,
        String bankName,
        String bankAccountNumber
) {
}
