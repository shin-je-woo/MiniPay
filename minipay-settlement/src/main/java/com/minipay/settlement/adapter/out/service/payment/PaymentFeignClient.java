package com.minipay.settlement.adapter.out.service.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@FeignClient(name = "payment-service", url = "${minipay.payment.url}")
public interface PaymentFeignClient {

    @GetMapping("/payments")
    ResponseEntity<PaymentListResponse> getPayments(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate,
            @RequestParam String paymentStatus
    );

    @PostMapping("/payments/{paymentId}/complete")
    ResponseEntity<Void> completePayment(@PathVariable UUID paymentId, @RequestBody CompletePaymentRequest request);
}
