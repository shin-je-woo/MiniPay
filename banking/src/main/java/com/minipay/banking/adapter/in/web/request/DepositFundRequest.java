package com.minipay.banking.adapter.in.web.request;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositFundRequest(
        UUID bankAccountId,
        BigDecimal amount
) {
}
