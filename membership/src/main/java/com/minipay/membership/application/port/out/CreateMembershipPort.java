package com.minipay.membership.application.port.out;

import com.minipay.membership.domain.Membership;

public interface CreateMembershipPort {
    Membership createMembership(Membership membership);
}
