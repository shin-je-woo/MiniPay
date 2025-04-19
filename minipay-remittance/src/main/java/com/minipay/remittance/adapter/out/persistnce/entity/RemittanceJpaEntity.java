package com.minipay.remittance.adapter.out.persistnce.entity;

import com.minipay.remittance.domain.Remittance;
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
@Table(name = "remittance")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemittanceJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("송금 트랜잭션 식별자")
    @Column(name = "remittance_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID remittanceId;

    @Comment("송금 요청자(회원) 식별자")
    @Column(name = "sender_id", nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID senderId;

    @Comment("수신자(회원) 식별자")
    @Column(name = "recipient_id", updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID recipientId;

    @Comment("수신자(외부) 은행명")
    @Column(name = "dest_bank_name", length = 50)
    private String destBankName;

    @Comment("수신자(외부) 계좌번호")
    @Column(name = "dest_bank_account_number", length = 30)
    private String destBankAccountNumber;

    @Comment("송금 금액")
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Comment("송금 유형 (내부회원/외부계좌)")
    @Enumerated(EnumType.STRING)
    @Column(name = "remittance_type", nullable = false, length = 30)
    private Remittance.RemittanceType remittanceType;

    @Comment("송금 상태")
    @Enumerated(EnumType.STRING)
    @Column(name = "remittance_status", nullable = false, length = 30)
    private Remittance.RemittanceStatus remittanceStatus;
}
