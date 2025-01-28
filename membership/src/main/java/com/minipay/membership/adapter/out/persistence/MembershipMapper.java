package com.minipay.membership.adapter.out.persistence;

import com.minipay.common.DomainMapper;
import com.minipay.membership.domain.Membership;

@DomainMapper
public class MembershipMapper {

    Membership mapToDomain(MembershipJpaEntity membershipJpaEntity) {
        return Membership.withId(
                new Membership.MembershipId(membershipJpaEntity.getId()),
                new Membership.MembershipName(membershipJpaEntity.getName()),
                new Membership.MembershipEmail(membershipJpaEntity.getEmail()),
                new Membership.MembershipAddress(membershipJpaEntity.getAddress()),
                membershipJpaEntity.isValid(),
                membershipJpaEntity.isCorp()
        );
    }

    MembershipJpaEntity mapToJpaEntity(Membership membership) {
        return MembershipJpaEntity.builder()
                .id(membership.getMembershipId() == null ? null : membership.getMembershipId().value())
                .name(membership.getName().value())
                .email(membership.getEmail().value())
                .address(membership.getAddress().value())
                .isValid(membership.isValid())
                .isCorp(membership.isCorp())
                .build();
    }
}
