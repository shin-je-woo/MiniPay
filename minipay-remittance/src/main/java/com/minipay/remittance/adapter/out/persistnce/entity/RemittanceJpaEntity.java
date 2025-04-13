package com.minipay.remittance.adapter.out.persistnce.entity;

import com.minipay.remittance.domain.Remittance;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    private UUID remittanceId;

    private UUID senderId;

    private UUID recipientId;

    private String destBankName;

    private String destBankAccountNumber;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Remittance.RemittanceType remittanceType;

    @Enumerated(EnumType.STRING)
    private Remittance.RemittanceStatus remittanceStatus;
}
