package com.minipay.settlement.adapter.out.service.money;

import java.math.BigDecimal;
import java.util.UUID;

public record MemberMoneyResponse(
        UUID memberMoneyId,
        UUID membershipId,
        UUID bankAccountId,
        BigDecimal balance
) {
}
