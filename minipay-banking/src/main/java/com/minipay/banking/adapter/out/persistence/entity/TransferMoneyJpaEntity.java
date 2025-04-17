package com.minipay.banking.adapter.out.persistence.entity;

import com.minipay.banking.domain.model.TransferMoney;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

@Getter
@Entity
@Builder
@Table(name = "transfer_money")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferMoneyJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transfer_money_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID transferMoneyId;

    private String srcBankName;

    private String srcAccountNumber;

    private String destBankName;

    private String destAccountNumber;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransferMoney.TransferMoneyStatus status;
}
