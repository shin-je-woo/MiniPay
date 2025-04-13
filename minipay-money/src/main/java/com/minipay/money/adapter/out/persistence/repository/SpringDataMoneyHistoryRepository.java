package com.minipay.money.adapter.out.persistence.repository;

import com.minipay.money.adapter.out.persistence.entity.MoneyHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataMoneyHistoryRepository extends JpaRepository<MoneyHistoryJpaEntity, Long> {
    Optional<MoneyHistoryJpaEntity> findByMoneyHistoryId(UUID moneyHistoryId);
}
