package com.minipay.payment.adapter.out.persistence.adapter;

import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.payment.adapter.out.persistence.entity.PaymentJpaEntity;
import com.minipay.payment.adapter.out.persistence.mapper.PaymentMapper;
import com.minipay.payment.adapter.out.persistence.repository.SpringDataPaymentRepository;
import com.minipay.payment.application.port.out.PaymentPersistencePort;
import com.minipay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentPersistencePort {

    private final SpringDataPaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public void createPayment(Payment payment) {
        PaymentJpaEntity paymentJpaEntity = paymentMapper.mapToJpaEntity(payment);
        paymentRepository.save(paymentJpaEntity);
    }
}
