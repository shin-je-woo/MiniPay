package com.minipay.settlement.adapter.out.service;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(
        UUID paymentId,
        UUID buyerId,
        UUID sellerId,
        BigDecimal price,
        BigDecimal feeRate,
        String paymentStatus
) {
}
