package com.minipay.remittance.adapter.out.service.money;

import java.math.BigDecimal;

public record IncreaseMoneyRequest(
        BigDecimal amount
) {
    public static IncreaseMoneyRequest from(BigDecimal amount) {
        return new IncreaseMoneyRequest(amount);
    }
}
