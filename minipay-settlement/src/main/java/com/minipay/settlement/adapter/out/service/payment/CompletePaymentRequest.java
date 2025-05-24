package com.minipay.settlement.adapter.out.service.payment;

import java.math.BigDecimal;

public record CompletePaymentRequest(
        BigDecimal paidAmount,
        BigDecimal feeAmount
) {
}
