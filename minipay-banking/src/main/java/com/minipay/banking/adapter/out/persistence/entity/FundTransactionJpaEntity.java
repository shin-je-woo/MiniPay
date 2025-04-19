package com.minipay.banking.adapter.out.persistence.entity;

import com.minipay.banking.domain.model.FundTransaction;
import com.minipay.banking.domain.model.MinipayBankAccount;
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
@Table(name = "fund_transaction")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundTransactionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("펀드 트랜잭션 식별자")
    @Column(name = "fund_transaction_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID fundTransactionId;

    @Comment("은행 계좌 식별자")
    @Column(name = "bank_account_id", nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID bankAccountId;

    @Comment("미니페이 내부 계좌 구분")
    @Enumerated(EnumType.STRING)
    @Column(name = "minipay_bank_account", nullable = false, length = 30)
    private MinipayBankAccount minipayBankAccount;

    @Comment("출금 은행 이름")
    @Column(name = "withdrawal_bank_name", length = 50)
    private String withdrawalBankName;

    @Comment("출금 계좌 번호")
    @Column(name = "withdrawal_account_number", length = 30)
    private String withdrawalAccountNumber;

    @Comment("자금 이동 타입 (WITHDRAWAL / DEPOSIT)")
    @Enumerated(EnumType.STRING)
    @Column(name = "fund_type", nullable = false, length = 20)
    private FundTransaction.FundType fundType;

    @Comment("이동 금액")
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Comment("자금 이동 상태")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private FundTransaction.FundTransactionStatus status;
}