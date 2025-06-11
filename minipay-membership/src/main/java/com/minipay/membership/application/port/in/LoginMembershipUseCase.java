package com.minipay.membership.application.port.in;

public interface LoginMembershipUseCase {
    LoginMembershipResult login(LoginMembershipCommand command);
    LoginMembershipResult reIssueToken(ReIssueTokenCommand command);
}
