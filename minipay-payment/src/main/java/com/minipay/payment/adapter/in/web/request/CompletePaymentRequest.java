package com.minipay.payment.adapter.in.web.request;

import java.math.BigDecimal;

public record CompletePaymentRequest(
        BigDecimal paidAmount,
        BigDecimal feeAmount
) {
}
