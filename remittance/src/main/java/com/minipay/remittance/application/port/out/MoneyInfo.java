package com.minipay.remittance.application.port.out;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record MoneyInfo(
        UUID memberMoneyId,
        BigDecimal balance
) {
}
