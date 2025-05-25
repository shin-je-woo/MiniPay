package com.minipay.settlement.adapter.out.service.payment;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.settlement.port.out.PaymentInfo;
import com.minipay.settlement.port.out.PaymentServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class PaymentServiceAdapter implements PaymentServicePort {

    private final PaymentFeignClient paymentFeignClient;
    private static final String  UNPAID_STATUS = "CREATED";

    @Override
    public List<PaymentInfo> getUnpaidPaymentsPaged(int page, int size, LocalDate fromDate, LocalDate toDate) {
        return Optional.ofNullable(paymentFeignClient.getPayments(page, size, fromDate, toDate, UNPAID_STATUS))
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

    @Override
    public boolean markPaymentSettled(UUID paymentId, BigDecimal settlementAmount, BigDecimal feeAmount) {
        CompletePaymentRequest request = new CompletePaymentRequest(settlementAmount, feeAmount);
        ResponseEntity<Void> response = paymentFeignClient.completePayment(paymentId, request);
        return response.getStatusCode().is2xxSuccessful();
    }
}
