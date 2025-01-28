package com.minipay.banking.adapter.out.persistence.repository;

import com.minipay.banking.adapter.out.persistence.entity.BankAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataBankAccountRepository extends JpaRepository<BankAccountJpaEntity, Long> {
}
