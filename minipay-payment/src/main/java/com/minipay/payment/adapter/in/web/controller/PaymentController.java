package com.minipay.payment.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.payment.adapter.in.web.request.CreatePaymentRequest;
import com.minipay.payment.adapter.in.web.response.PaymentResponse;
import com.minipay.payment.application.port.in.CreatePaymentCommand;
import com.minipay.payment.application.port.in.PaymentUseCase;
import com.minipay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @PostMapping(path = "/payment")
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
}
