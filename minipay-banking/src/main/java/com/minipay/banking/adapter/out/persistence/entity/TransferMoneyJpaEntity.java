package com.minipay.banking.adapter.out.persistence.entity;

import com.minipay.banking.domain.model.TransferMoney;
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
@Table(name = "transfer_money")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferMoneyJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("송금 식별자")
    @Column(name = "transfer_money_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID transferMoneyId;

    @Comment("송금 출금 은행명")
    @Column(name = "src_bank_name", nullable = false, length = 50)
    private String srcBankName;

    @Comment("송금 출금 계좌번호")
    @Column(name = "src_account_number", nullable = false, length = 30)
    private String srcAccountNumber;

    @Comment("송금 입금 은행명")
    @Column(name = "dest_bank_name", nullable = false, length = 50)
    private String destBankName;

    @Comment("송금 입금 계좌번호")
    @Column(name = "dest_account_number", nullable = false, length = 30)
    private String destAccountNumber;

    @Comment("송금 금액")
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Comment("송금 상태")
    @Enumerated(EnumType.STRING)
    private TransferMoney.TransferMoneyStatus status;
}
