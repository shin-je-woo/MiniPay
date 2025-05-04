package com.minipay.remittance.application.port.out;

import java.math.BigDecimal;
import java.util.UUID;

public record MoneyInfo(
        UUID memberMoneyId,
        UUID bankAccountId,
        BigDecimal balance
) {
}
