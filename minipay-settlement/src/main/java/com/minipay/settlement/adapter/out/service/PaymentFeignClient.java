package com.minipay.settlement.adapter.out.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service", url = "${minipay.payment.url}")
public interface PaymentFeignClient {

    @GetMapping("/payments")
    ResponseEntity<PaymentListResponse> getPayments(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String paymentStatus
    );
}
