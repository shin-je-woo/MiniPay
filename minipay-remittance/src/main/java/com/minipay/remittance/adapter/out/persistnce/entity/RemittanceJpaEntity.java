package com.minipay.remittance.adapter.out.persistnce.entity;

import com.minipay.remittance.domain.Remittance;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

@Getter
@Entity
@Builder
@Table(name = "remittance")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemittanceJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "remittance_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID remittanceId;

    @Column(name = "sender_id", nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID senderId;

    @Column(name = "recipient_id", updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID recipientId;

    private String destBankName;

    private String destBankAccountNumber;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Remittance.RemittanceType remittanceType;

    @Enumerated(EnumType.STRING)
    private Remittance.RemittanceStatus remittanceStatus;
}
