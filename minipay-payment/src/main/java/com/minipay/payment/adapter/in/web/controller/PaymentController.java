package com.minipay.payment.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.payment.adapter.in.web.request.CreatePaymentRequest;
import com.minipay.payment.adapter.in.web.response.PaymentListResponse;
import com.minipay.payment.adapter.in.web.response.PaymentResponse;
import com.minipay.payment.application.port.in.CreatePaymentCommand;
import com.minipay.payment.application.port.in.GetPaymentUseCase;
import com.minipay.payment.application.port.in.PaymentUseCase;
import com.minipay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentUseCase paymentUseCase;
    private final GetPaymentUseCase getPaymentUseCase;

    @PostMapping("/payments")
    ResponseEntity<PaymentResponse> createPayment(CreatePaymentRequest request) {
        CreatePaymentCommand command = new CreatePaymentCommand(
                request.buyerId(),
                request.sellerId(),
                request.price(),
                request.feeRate()
        );
        Payment payment = paymentUseCase.createPayment(command);
        return ResponseEntity.ok(PaymentResponse.from(payment));
    }

    @GetMapping("/payments")
    ResponseEntity<PaymentListResponse> getPayments(@RequestParam Payment.PaymentStatus paymentStatus) {
        List<Payment> payments = getPaymentUseCase.getPayments(paymentStatus);
        return ResponseEntity.ok(PaymentListResponse.from(payments));
    }
}
