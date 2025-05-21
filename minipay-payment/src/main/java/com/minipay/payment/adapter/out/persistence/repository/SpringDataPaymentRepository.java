package com.minipay.payment.adapter.out.persistence.repository;

import com.minipay.payment.adapter.out.persistence.entity.PaymentJpaEntity;
import com.minipay.payment.domain.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataPaymentRepository extends JpaRepository<PaymentJpaEntity, Long> {
    Optional<PaymentJpaEntity> findByPaymentId(UUID paymentId);
    List<PaymentJpaEntity> findAllByPaymentStatus(Pageable pageable, Payment.PaymentStatus paymentStatus);
}
