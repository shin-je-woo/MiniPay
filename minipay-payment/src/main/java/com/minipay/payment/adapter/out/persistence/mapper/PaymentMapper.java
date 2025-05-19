package com.minipay.payment.adapter.out.persistence.mapper;

import com.minipay.common.annotation.DomainMapper;
import com.minipay.payment.adapter.out.persistence.entity.PaymentJpaEntity;
import com.minipay.payment.domain.Money;
import com.minipay.payment.domain.Payment;

@DomainMapper
public class PaymentMapper {

    public Payment mapToDomain(PaymentJpaEntity payment) {
        return Payment.withId(
                new Payment.PaymentId(payment.getPaymentId()),
                new Payment.MembershipId(payment.getBuyerId()),
                new Payment.MembershipId(payment.getSellerId()),
                new Money(payment.getPrice()),
                new Payment.FeeRate(payment.getFeeRate()),
                payment.getPaymentStatus()
        );
    }

    public PaymentJpaEntity mapToJpaEntity(Payment payment) {
        return PaymentJpaEntity.builder()
                .paymentId(payment.getPaymentId().value())
                .buyerId(payment.getBuyerId().value())
                .sellerId(payment.getSellerId().value())
                .price(payment.getPrice().value())
                .feeRate(payment.getFeeRate().value())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }
}
