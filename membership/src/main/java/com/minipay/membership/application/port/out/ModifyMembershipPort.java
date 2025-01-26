package com.minipay.membership.application.port.out;

import com.minipay.membership.domain.Membership;

public interface ModifyMembershipPort {
    Membership modifyMembership(Membership membership);
}
