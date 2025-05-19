package com.minipay.payment.adapter.out.persistence.entity;

import com.minipay.payment.domain.Payment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
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

    @Comment("결제 식별자")
    @Column(name = "payment_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID paymentId;

    @Comment("구매자(회원) 식별자")
    @Column(name = "buyer_id", nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID buyerId;

    @Comment("판매자(회원) 식별자")
    @Column(name = "seller_id", nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID sellerId;

    @Comment("결제 금액")
    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Comment("수수료율 (예: 0.25)")
    @Column(name = "fee_rate", nullable = false, precision = 6, scale = 4)
    private BigDecimal feeRate;

    @Comment("결제 상태")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 30)
    private Payment.PaymentStatus paymentStatus;

    @Comment("정산 금액")
    @Column(name = "paid_amount", precision = 19, scale = 2)
    private BigDecimal paidAmount;

    @Comment("수수료 금액")
    @Column(name = "fee_amount", precision = 19, scale = 2)
    private BigDecimal feeAmount;
}
