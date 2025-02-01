package com.minipay.membership.adapter.out.persistence.repository;

import com.minipay.membership.adapter.out.persistence.entity.MembershipJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMembershipRepository extends JpaRepository<MembershipJpaEntity, Long> {
}
