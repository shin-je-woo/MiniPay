package com.minipay.banking.adapter.out.persistence.repository;

import com.minipay.banking.adapter.out.persistence.entity.FundTransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataFundTransactionRepository extends JpaRepository<FundTransactionJpaEntity, Long> {
}
