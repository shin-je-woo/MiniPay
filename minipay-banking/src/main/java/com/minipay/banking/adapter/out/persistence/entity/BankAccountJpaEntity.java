package com.minipay.banking.adapter.out.persistence.entity;

import com.minipay.banking.domain.model.BankAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Builder
@Table(name = "bank_account")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankAccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID bankAccountId;

    private UUID membershipId;

    private String bankName;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private BankAccount.LinkedStatus linkedStatus;
}
