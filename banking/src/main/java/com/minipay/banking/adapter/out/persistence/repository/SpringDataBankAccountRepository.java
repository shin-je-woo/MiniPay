package com.minipay.banking.adapter.out.persistence.repository;

import com.minipay.banking.adapter.out.persistence.entity.BankAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataBankAccountRepository extends JpaRepository<BankAccountJpaEntity, Long> {
    Optional<BankAccountJpaEntity> findByBankAccountId(UUID bankAccountId);
}
