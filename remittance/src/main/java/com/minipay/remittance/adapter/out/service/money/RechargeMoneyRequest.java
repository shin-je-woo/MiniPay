package com.minipay.remittance.adapter.out.service.money;

import java.math.BigDecimal;

public record RechargeMoneyRequest(
        BigDecimal amount
) {
    public static RechargeMoneyRequest from(BigDecimal amount) {
        return new RechargeMoneyRequest(amount);
    }
}
