package com.minipay.membership.application.port.in;

public interface LoginMembershipUseCase {
    LoginMembershipResult login(LoginMembershipCommand command);
}
