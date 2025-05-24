package com.minipay.settlement.port.out;

import java.util.UUID;

public record MoneyInfo(
        UUID memberMoneyId,
        UUID bankAccountId
) {
}
