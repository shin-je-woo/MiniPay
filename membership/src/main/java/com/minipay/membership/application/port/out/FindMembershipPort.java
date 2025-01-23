package com.minipay.membership.application.port.out;

import com.minipay.membership.domain.Membership;

import java.util.Optional;

public interface FindMembershipPort {
    Optional<Membership> findMember(Membership.MembershipId membershipId);
}
