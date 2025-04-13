package com.minipay.membership.application.port.in;

import com.minipay.membership.domain.Membership;

import java.util.UUID;

public interface GetMembershipQuery {
    Membership getMembership(UUID membershipId);
}
