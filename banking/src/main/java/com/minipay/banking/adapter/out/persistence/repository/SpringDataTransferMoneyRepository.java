package com.minipay.banking.adapter.out.persistence.repository;

import com.minipay.banking.adapter.out.persistence.entity.TransferMoneyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTransferMoneyRepository extends JpaRepository<TransferMoneyJpaEntity, Integer> {
}
