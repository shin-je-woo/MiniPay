package com.minipay.membership.application.port.in;

import com.minipay.membership.domain.Membership;

import java.util.List;
import java.util.UUID;

public interface GetMembershipUseCase {
    Membership getMembership(UUID membershipId);
    List<Membership> getMembershipByAddress(String address);
}
