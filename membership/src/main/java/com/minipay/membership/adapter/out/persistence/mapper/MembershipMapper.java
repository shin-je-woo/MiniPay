package com.minipay.membership.adapter.out.persistence.mapper;

import com.minipay.common.annotation.DomainMapper;
import com.minipay.membership.adapter.out.persistence.entity.MembershipJpaEntity;
import com.minipay.membership.domain.Membership;

@DomainMapper
public class MembershipMapper {

    public Membership mapToDomain(MembershipJpaEntity membershipJpaEntity) {
        return Membership.withId(
                new Membership.MembershipId(membershipJpaEntity.getMembershipId()),
                new Membership.MembershipName(membershipJpaEntity.getName()),
                new Membership.MembershipEmail(membershipJpaEntity.getEmail()),
                new Membership.MembershipAddress(membershipJpaEntity.getAddress()),
                membershipJpaEntity.isValid(),
                membershipJpaEntity.isCorp()
        );
    }

    public MembershipJpaEntity mapToJpaEntity(Membership membership) {
        return MembershipJpaEntity.builder()
                .membershipId(membership.getMembershipId().value())
                .name(membership.getName().value())
                .email(membership.getEmail().value())
                .address(membership.getAddress().value())
                .isValid(membership.isValid())
                .isCorp(membership.isCorp())
                .build();
    }
}
