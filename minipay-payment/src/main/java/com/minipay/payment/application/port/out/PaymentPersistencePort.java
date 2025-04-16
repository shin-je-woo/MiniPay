package com.minipay.payment.application.port.out;

import com.minipay.payment.domain.Payment;

public interface PaymentPersistencePort {
    void createPayment(Payment payment);
}
