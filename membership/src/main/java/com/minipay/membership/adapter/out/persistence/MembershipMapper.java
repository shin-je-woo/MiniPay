package com.minipay.membership.adapter.out.persistence;

import com.minipay.membership.domain.Membership;
import common.DomainMapper;

@DomainMapper
public class MembershipMapper {

    Membership mapToDomain(MembershipJpaEntity membershipJpaEntity) {
        return  Membership.generateMember(
                new Membership.MembershipId(String.valueOf(membershipJpaEntity.getMembershipId())),
                new Membership.MembershipName(membershipJpaEntity.getName()),
                new Membership.MembershipEmail(membershipJpaEntity.getEmail()),
                new Membership.MembershipAddress(membershipJpaEntity.getAddress()),
                new Membership.MembershipIsValid(membershipJpaEntity.isValid()),
                new Membership.MembershipIsCorp(membershipJpaEntity.isCorp())
        );
    }
}
