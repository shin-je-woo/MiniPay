package com.minipay.payment.application.port.in;

import com.minipay.payment.domain.Payment;

import java.util.List;

public interface GetPaymentUseCase {
    List<Payment> getPaymentsPaged(int page, int size, Payment.PaymentStatus paymentStatus);
}
