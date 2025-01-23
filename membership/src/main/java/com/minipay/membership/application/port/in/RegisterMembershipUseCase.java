package com.minipay.membership.application.port.in;

import com.minipay.membership.domain.Membership;

public interface RegisterMembershipUseCase {
    Membership registerMembership(RegisterMembershipCommand command);
}
