package com.minipay.payment.adapter.out.persistence.entity;

import com.minipay.payment.domain.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@Builder
@Table(name = "payment")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID paymentId;

    private UUID buyerId;

    private UUID sellerId;

    private BigDecimal price;

    private BigDecimal feeRate;

    @Enumerated(EnumType.STRING)
    private Payment.PaymentStatus paymentStatus;
}
