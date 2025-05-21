package com.minipay.settlement.adapter.out.service;

import java.util.List;

public record PaymentListResponse(
        List<PaymentResponse> payments
) {
}
