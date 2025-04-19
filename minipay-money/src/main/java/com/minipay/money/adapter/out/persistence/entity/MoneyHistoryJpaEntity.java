package com.minipay.money.adapter.out.persistence.entity;

import com.minipay.money.domain.model.MoneyHistory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
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

    @Comment("머니 변경 내역 식별자")
    @Column(name = "money_history_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID moneyHistoryId;

    @Comment("회원 머니 식별자")
    @Column(name = "member_money_id", nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID memberMoneyId;

    @Comment("변경 타입 (충전, 증액, 감액 등)")
    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = false, length = 30)
    private MoneyHistory.ChangeType changeType;

    @Comment("변경 상태")
    @Enumerated(EnumType.STRING)
    @Column(name = "change_status", nullable = false, length = 30)
    private MoneyHistory.ChangeStatus changeStatus;

    @Comment("변경 금액")
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Comment("변경 후 잔액")
    @Column(name = "after_balance", precision = 19, scale = 2)
    private BigDecimal afterBalance;

    @Comment("생성 일시")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Comment("수정 일시")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
