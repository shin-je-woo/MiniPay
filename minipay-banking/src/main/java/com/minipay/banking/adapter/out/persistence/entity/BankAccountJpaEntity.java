package com.minipay.banking.adapter.out.persistence.entity;

import com.minipay.banking.domain.model.BankAccount;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
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

    @Comment("은행 계좌 식별자")
    @Column(name = "bank_account_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID bankAccountId;

    @Comment("회원 식별자")
    @Column(name = "membership_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID membershipId;

    @Comment("은행 이름")
    @Column(name = "bank_name", nullable = false, length = 50)
    private String bankName;

    @Comment("계좌 번호")
    @Column(name = "account_number", nullable = false, length = 30)
    private String accountNumber;

    @Comment("계좌 연동 상태")
    @Enumerated(EnumType.STRING)
    @Column(name = "linked_status", nullable = false)
    private BankAccount.LinkedStatus linkedStatus;
}
