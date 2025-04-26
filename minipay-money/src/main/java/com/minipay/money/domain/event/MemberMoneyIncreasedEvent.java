package com.minipay.money.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

public record MemberMoneyIncreasedEvent(
        UUID memberMoneyId,
        BigDecimal amount
) {
}
