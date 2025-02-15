package com.minipay.money.adapter.out.persistence.repository;

import com.minipay.money.adapter.out.persistence.entity.MemberMoneyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataMemberMoneyRepository extends JpaRepository<MemberMoneyJpaEntity, Long> {
    Optional<MemberMoneyJpaEntity> findByMemberMoneyId(UUID memberMoneyId);
}
