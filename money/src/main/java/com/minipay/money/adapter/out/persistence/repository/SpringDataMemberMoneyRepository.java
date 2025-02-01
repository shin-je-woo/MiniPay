package com.minipay.money.adapter.out.persistence.repository;

import com.minipay.money.adapter.out.persistence.entity.MemberMoneyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMemberMoneyRepository extends JpaRepository<MemberMoneyJpaEntity, Long> {
}
