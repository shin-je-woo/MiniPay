package com.minipay.membership.adapter.out.persistence;

import com.minipay.common.DomainMapper;
import com.minipay.membership.domain.Membership;

@DomainMapper
public class MembershipMapper {

    Membership mapToDomain(MembershipJpaEntity membershipJpaEntity) {
        return  Membership.withId(
                new Membership.MembershipId(String.valueOf(membershipJpaEntity.getMembershipId())),
                new Membership.MembershipName(membershipJpaEntity.getName()),
                new Membership.MembershipEmail(membershipJpaEntity.getEmail()),
                new Membership.MembershipAddress(membershipJpaEntity.getAddress()),
                new Membership.MembershipIsValid(membershipJpaEntity.isValid()),
                new Membership.MembershipIsCorp(membershipJpaEntity.isCorp())
        );
    }

    MembershipJpaEntity mapToJpaEntity(Membership membership) {
        return MembershipJpaEntity.builder()
                .membershipId(membership.getMembershipId() == null ? null : Long.parseLong(membership.getMembershipId()))
                .name(membership.getName())
                .email(membership.getEmail())
                .address(membership.getAddress())
                .isValid(membership.isValid())
                .isCorp(membership.isCorp())
                .build();
    }
}
