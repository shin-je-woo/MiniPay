package com.minipay.money.adapter.out.persistence.repository;


import com.minipay.money.adapter.out.persistence.entity.OutboxJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataOutboxRepository extends JpaRepository<OutboxJpaEntity, Long> {

    List<OutboxJpaEntity> findAllByPublishedFalseOrderByIdAsc();
    OutboxJpaEntity findByEventUuid(UUID uuid);
}
