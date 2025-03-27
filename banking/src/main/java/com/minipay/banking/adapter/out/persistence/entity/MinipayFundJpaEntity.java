package com.minipay.banking.adapter.out.persistence.entity;

import com.minipay.banking.domain.model.MinipayBankAccount;
import com.minipay.banking.domain.model.MinipayFund;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@Builder
@Table(name = "minipay_fund")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinipayFundJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID minipayFundId;

    private UUID bankAccountId;

    @Enumerated(EnumType.STRING)
    private MinipayBankAccount minipayBankAccount;

    @Enumerated(EnumType.STRING)
    private MinipayFund.FundType fundType;

    private BigDecimal amount;
}
