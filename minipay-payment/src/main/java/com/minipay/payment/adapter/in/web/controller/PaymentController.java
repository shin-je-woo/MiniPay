package com.minipay.payment.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.payment.adapter.in.web.request.CompletePaymentRequest;
import com.minipay.payment.adapter.in.web.request.CreatePaymentRequest;
import com.minipay.payment.adapter.in.web.response.PaymentListResponse;
import com.minipay.payment.adapter.in.web.response.PaymentResponse;
import com.minipay.payment.application.port.in.CompletePaymentCommand;
import com.minipay.payment.application.port.in.CreatePaymentCommand;
import com.minipay.payment.application.port.in.GetPaymentUseCase;
import com.minipay.payment.application.port.in.PaymentUseCase;
import com.minipay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping("/payments/{paymentId}/complete")
    ResponseEntity<Void> completePayment(@RequestParam UUID paymentId, @RequestBody CompletePaymentRequest request) {
        CompletePaymentCommand command = CompletePaymentCommand.builder()
                .paymentId(paymentId)
                .paidAmount(request.paidAmount())
                .feeAmount(request.feeAmount())
                .build();
        paymentUseCase.completePayment(command);
        return ResponseEntity.ok().build();
    }
}
