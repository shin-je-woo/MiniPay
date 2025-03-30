package com.minipay.banking.adapter.out.persistence.repository;

import com.minipay.banking.adapter.out.persistence.entity.FundTransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataFundTransactionRepository extends JpaRepository<FundTransactionJpaEntity, Long> {
    Optional<FundTransactionJpaEntity> findByFundTransactionId(UUID fundTransactionId);
}
