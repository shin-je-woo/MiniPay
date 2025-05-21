package com.minipay.payment.adapter.out.persistence.adapter;

import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.common.exception.DataNotFoundException;
import com.minipay.payment.adapter.out.persistence.entity.PaymentJpaEntity;
import com.minipay.payment.adapter.out.persistence.mapper.PaymentMapper;
import com.minipay.payment.adapter.out.persistence.repository.SpringDataPaymentRepository;
import com.minipay.payment.application.port.out.PaymentPersistencePort;
import com.minipay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

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

    @Override
    public Payment readPayment(Payment.PaymentId paymentId) {
        return paymentRepository.findByPaymentId(paymentId.value())
                .map(paymentMapper::mapToDomain)
                .orElseThrow(() -> new DataNotFoundException("Payment not found"));
    }

    @Override
    public List<Payment> readPaymentsPaged(int page, int size, Payment.PaymentStatus paymentStatus) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return paymentRepository.findAllByPaymentStatus(pageable, paymentStatus).stream()
                .map(paymentMapper::mapToDomain)
                .toList();
    }

    @Override
    public void updatePayment(Payment payment) {
        PaymentJpaEntity existingEntity = paymentRepository.findByPaymentId(payment.getPaymentId().value())
                .orElseThrow(() -> new DataNotFoundException("Payment not found"));

        PaymentJpaEntity paymentJpaEntity = paymentMapper.mapToExistingJpaEntity(payment, existingEntity.getId());
        paymentRepository.save(paymentJpaEntity);
    }
}
