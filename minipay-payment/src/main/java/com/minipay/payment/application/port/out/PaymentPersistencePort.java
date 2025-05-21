package com.minipay.payment.application.port.out;

import com.minipay.payment.domain.Payment;

import java.util.List;

public interface PaymentPersistencePort {
    void createPayment(Payment payment);
    Payment readPayment(Payment.PaymentId paymentId);
    List<Payment> readPaymentsPaged(int page, int size, Payment.PaymentStatus paymentStatus);
    void updatePayment(Payment payment);
}
