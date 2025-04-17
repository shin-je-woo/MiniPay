package com.minipay.banking.adapter.out.persistence.entity;

import com.minipay.banking.domain.model.BankAccount;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
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

    @Column(name = "bank_account_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID bankAccountId;

    @Column(name = "membership_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID membershipId;

    private String bankName;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private BankAccount.LinkedStatus linkedStatus;
}
