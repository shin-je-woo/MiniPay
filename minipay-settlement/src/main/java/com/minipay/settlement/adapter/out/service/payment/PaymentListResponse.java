package com.minipay.settlement.adapter.out.service.payment;

import java.util.List;

public record PaymentListResponse(
        List<PaymentResponse> payments
) {
}
