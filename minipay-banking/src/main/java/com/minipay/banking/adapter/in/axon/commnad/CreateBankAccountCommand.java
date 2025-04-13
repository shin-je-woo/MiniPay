package com.minipay.banking.adapter.in.axon.commnad;

import java.util.UUID;

public record CreateBankAccountCommand(
        UUID membershipId,
        String bankName,
        String accountNumber
) {
}
