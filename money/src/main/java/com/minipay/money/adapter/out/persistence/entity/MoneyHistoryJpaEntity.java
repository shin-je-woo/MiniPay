package com.minipay.money.adapter.out.persistence.entity;

import com.minipay.money.domain.MoneyHistory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(nullable = false, updatable = false)
    private Long memberMoneyId;

    @Enumerated(EnumType.STRING)
    private MoneyHistory.ChangeType changeType;

    private BigDecimal amount;

    private BigDecimal afterBalance;

    private LocalDateTime createdAt;
}
