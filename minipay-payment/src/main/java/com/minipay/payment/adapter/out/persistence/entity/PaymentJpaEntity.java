package com.minipay.payment.adapter.out.persistence.entity;

import com.minipay.payment.domain.Payment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
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

    @Column(name = "payment_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID paymentId;

    @Column(name = "buyer_id", nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID buyerId;

    @Column(name = "seller_id", nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID sellerId;

    private BigDecimal price;

    private BigDecimal feeRate;

    @Enumerated(EnumType.STRING)
    private Payment.PaymentStatus paymentStatus;
}
