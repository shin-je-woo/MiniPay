package com.minipay.payment.adapter.out.persistence.repository;

import com.minipay.payment.adapter.out.persistence.entity.PaymentJpaEntity;
import com.minipay.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataPaymentRepository extends JpaRepository<PaymentJpaEntity, Long> {
    List<PaymentJpaEntity> findAllByPaymentStatus(Payment.PaymentStatus paymentStatus);
}
