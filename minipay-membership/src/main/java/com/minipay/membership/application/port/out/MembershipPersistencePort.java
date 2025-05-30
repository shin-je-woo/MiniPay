package com.minipay.membership.application.port.out;

import com.minipay.membership.domain.Membership;

import java.util.List;

public interface MembershipPersistencePort {
    Membership createMembership(Membership membership);
    Membership readMembership(Membership.MembershipId membershipId);
    Membership readMembershipByEmail(Membership.MembershipEmail email);
    Membership updateMembership(Membership membership);
    List<Membership> readMembershipByAddress(Membership.MembershipAddress address);
}
