package com.minipay.payment.adapter.in.web.response;

import com.minipay.payment.domain.Payment;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(
        UUID paymentId,
        UUID buyerId,
        UUID sellerId,
        BigDecimal price,
        BigDecimal feeRate,
        Payment.PaymentStatus paymentStatus
) {
    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getPaymentId().value(),
                payment.getBuyerId().value(),
                payment.getSellerId().value(),
                payment.getPrice().value(),
                payment.getFeeRate().value(),
                payment.getPaymentStatus()
        );
    }
}
