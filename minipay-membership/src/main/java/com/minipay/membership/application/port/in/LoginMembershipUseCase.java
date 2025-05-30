package com.minipay.membership.application.port.in;

import com.minipay.membership.domain.Membership;

public interface LoginMembershipUseCase {
    Membership login(LoginMembershipCommand command);
}
