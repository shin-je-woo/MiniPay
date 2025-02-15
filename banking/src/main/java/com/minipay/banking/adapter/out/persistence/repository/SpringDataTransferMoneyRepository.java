package com.minipay.banking.adapter.out.persistence.repository;

import com.minipay.banking.adapter.out.persistence.entity.TransferMoneyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataTransferMoneyRepository extends JpaRepository<TransferMoneyJpaEntity, Integer> {
    Optional<TransferMoneyJpaEntity> findByTransferMoneyId(UUID transferMoneyId);
}
