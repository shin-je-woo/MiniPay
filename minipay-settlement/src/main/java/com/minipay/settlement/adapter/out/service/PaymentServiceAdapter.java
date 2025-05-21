package com.minipay.settlement.adapter.out.service;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.settlement.port.out.PaymentInfo;
import com.minipay.settlement.port.out.PaymentServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class PaymentServiceAdapter implements PaymentServicePort {

    private final PaymentFeignClient paymentFeignClient;
    private static final String  UNPAID_STATUS = "CREATED";

    @Override
    public List<PaymentInfo> getUnpaidPaymentsPaged(int page, int size) {
        return Optional.ofNullable(paymentFeignClient.getPayments(page, size, UNPAID_STATUS))
                .map(ResponseEntity::getBody)
                .map(paymentListResponse -> paymentListResponse.payments().stream()
                        .map(paymentResponse -> new PaymentInfo(
                                paymentResponse.paymentId(),
                                paymentResponse.buyerId(),
                                paymentResponse.sellerId(),
                                paymentResponse.price(),
                                paymentResponse.feeRate(),
                                paymentResponse.paymentStatus()
                        ))
                        .toList())
                .orElse(Collections.emptyList());
    }
}
