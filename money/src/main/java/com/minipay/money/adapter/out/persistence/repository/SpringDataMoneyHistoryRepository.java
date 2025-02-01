package com.minipay.money.adapter.out.persistence.repository;

import com.minipay.money.adapter.out.persistence.entity.MoneyHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMoneyHistoryRepository extends JpaRepository<MoneyHistoryJpaEntity, Long> {
}
