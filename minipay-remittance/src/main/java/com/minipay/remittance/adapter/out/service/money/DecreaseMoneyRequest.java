package com.minipay.remittance.adapter.out.service.money;

import java.math.BigDecimal;

public record DecreaseMoneyRequest(
        BigDecimal amount
) {
    public static DecreaseMoneyRequest from(BigDecimal amount) {
        return new DecreaseMoneyRequest(amount);
    }
}
