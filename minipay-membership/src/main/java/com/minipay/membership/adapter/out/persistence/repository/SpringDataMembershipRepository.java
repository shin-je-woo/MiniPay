package com.minipay.membership.adapter.out.persistence.repository;

import com.minipay.membership.adapter.out.persistence.entity.MembershipJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataMembershipRepository extends JpaRepository<MembershipJpaEntity, Long> {
    Optional<MembershipJpaEntity> findByMembershipId(UUID memberId);
    Optional<MembershipJpaEntity> findByEmail(String email);
    List<MembershipJpaEntity> findByAddress(String address);
}
