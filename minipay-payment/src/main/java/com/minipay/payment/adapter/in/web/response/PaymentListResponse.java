package com.minipay.payment.adapter.in.web.response;

import com.minipay.payment.domain.Payment;

import java.util.List;

public record PaymentListResponse(
        List<PaymentResponse> payments
) {
    public static PaymentListResponse from(List<Payment> payments) {
        List<PaymentResponse> paymentResponses = payments.stream()
                .map(PaymentResponse::from)
                .toList();
        return new PaymentListResponse(paymentResponses);
    }
}
