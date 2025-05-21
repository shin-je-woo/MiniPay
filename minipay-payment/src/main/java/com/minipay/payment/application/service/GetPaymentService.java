package com.minipay.payment.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.payment.application.port.in.GetPaymentUseCase;
import com.minipay.payment.application.port.out.PaymentPersistencePort;
import com.minipay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetPaymentService implements GetPaymentUseCase {

    private final PaymentPersistencePort paymentPersistencePort;

    @Override
    public List<Payment> getPaymentsPaged(int page, int size, Payment.PaymentStatus paymentStatus) {
        return paymentPersistencePort.readPaymentsPaged(page, size, paymentStatus);
    }
}
