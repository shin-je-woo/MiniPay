package com.minipay.payment.adapter.out.persistence.repository;

import com.minipay.payment.adapter.out.persistence.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPaymentRepository extends JpaRepository<PaymentJpaEntity, Long> {
}
