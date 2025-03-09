package com.minipay.remittance.adapter.out.persistnce.repository;

import com.minipay.remittance.adapter.out.persistnce.entity.RemittanceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataRemittanceRepository extends JpaRepository<RemittanceJpaEntity, Long> {
    Optional<RemittanceJpaEntity> findByRemittanceId(UUID remittanceId);
}
