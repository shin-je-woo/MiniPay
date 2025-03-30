package com.minipay.banking.adapter.out.persistence.entity;

import com.minipay.banking.domain.model.FundTransaction;
import com.minipay.banking.domain.model.MinipayBankAccount;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @Column(unique = true, nullable = false)
    private UUID fundTransactionId;

    private UUID bankAccountId;

    @Enumerated(EnumType.STRING)
    private MinipayBankAccount minipayBankAccount;

    private String withdrawalBankName;

    private String withdrawalAccountNumber;

    @Enumerated(EnumType.STRING)
    private FundTransaction.FundType fundType;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private FundTransaction.FundTransactionStatus status;
}
