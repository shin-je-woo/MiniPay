package com.minipay.membership.application.port.out;

import com.minipay.membership.domain.Membership;

public interface MembershipPersistencePort {
    Membership createMembership(Membership membership);
    Membership readMembership(Membership.MembershipId membershipId);
    Membership updateMembership(Membership membership);
}
