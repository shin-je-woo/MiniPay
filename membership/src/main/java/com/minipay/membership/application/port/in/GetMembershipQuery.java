package com.minipay.membership.application.port.in;

import com.minipay.membership.domain.Membership;

public interface GetMembershipQuery {
    Membership getMembership(Long membershipId);
}
