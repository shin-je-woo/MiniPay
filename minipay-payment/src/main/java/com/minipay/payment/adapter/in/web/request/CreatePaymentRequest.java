package com.minipay.payment.adapter.in.web.request;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentRequest(
        UUID buyerId,
        UUID sellerId,
        BigDecimal price,
        BigDecimal feeRate
) {
}
