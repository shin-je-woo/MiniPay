package com.minipay.settlement.port.out;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentInfo(
        UUID paymentId,
        UUID buyerId,
        UUID sellerId,
        BigDecimal price,
        BigDecimal feeRate,
        String paymentStatus
) {
}
