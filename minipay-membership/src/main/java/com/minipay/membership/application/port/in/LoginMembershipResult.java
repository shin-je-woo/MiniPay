package com.minipay.membership.application.port.in;

public record LoginMembershipResult(
    String accessToken,
    String refreshToken
) {
}
