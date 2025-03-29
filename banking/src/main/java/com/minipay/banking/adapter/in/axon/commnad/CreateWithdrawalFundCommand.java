package com.minipay.banking.adapter.in.axon.commnad;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateWithdrawalFundCommand(
        UUID bankAccountId,
        BigDecimal amount
) {
}
