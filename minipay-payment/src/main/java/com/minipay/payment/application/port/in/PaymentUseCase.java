package com.minipay.payment.application.port.in;

import com.minipay.payment.domain.Payment;

public interface PaymentUseCase {
    Payment createPayment(CreatePaymentCommand command);
    void completePayment(CompletePaymentCommand command);
}
