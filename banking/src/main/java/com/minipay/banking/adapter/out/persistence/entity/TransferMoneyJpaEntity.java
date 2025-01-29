package com.minipay.banking.adapter.out.persistence.entity;

import com.minipay.banking.domain.TransferMoney;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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

    private String fromBankName;

    private String fromBankAccountNumber;

    private String toBankName;

    private String toBankAccountNumber;

    private BigDecimal moneyAmount;

    @Enumerated(EnumType.STRING)
    private TransferMoney.TransferMoneyStatus status;
}
