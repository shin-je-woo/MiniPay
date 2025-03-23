package com.minipay.money.adapter.axon.event;

import java.math.BigDecimal;
import java.util.UUID;

public record MemberMoneyCreatedEvent(
        UUID memberMoneyId,
        UUID membershipId,
        UUID bankAccountId,
        BigDecimal balance
) {
}
