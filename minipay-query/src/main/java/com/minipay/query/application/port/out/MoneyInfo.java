package com.minipay.query.application.port.out;

import java.math.BigDecimal;
import java.util.UUID;

public record MoneyInfo(
        UUID memberMoneyId,
        UUID membershipId,
        BigDecimal balance
) {
}
