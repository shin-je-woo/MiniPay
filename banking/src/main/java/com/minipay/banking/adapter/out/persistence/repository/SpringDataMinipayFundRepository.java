package com.minipay.banking.adapter.out.persistence.repository;

import com.minipay.banking.adapter.out.persistence.entity.MinipayFundJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMinipayFundRepository extends JpaRepository<MinipayFundJpaEntity, Long> {
}
