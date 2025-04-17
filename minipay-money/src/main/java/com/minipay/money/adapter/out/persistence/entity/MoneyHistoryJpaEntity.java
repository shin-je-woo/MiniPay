package com.minipay.money.adapter.out.persistence.entity;

import com.minipay.money.domain.model.MoneyHistory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Builder
@Table(name = "money_history")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoneyHistoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "money_history_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID moneyHistoryId;

    @Column(name = "member_money_id", nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID memberMoneyId;

    @Enumerated(EnumType.STRING)
    private MoneyHistory.ChangeType changeType;

    @Enumerated(EnumType.STRING)
    private MoneyHistory.ChangeStatus changeStatus;

    private BigDecimal amount;

    private BigDecimal afterBalance;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
