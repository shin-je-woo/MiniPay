package com.minipay.membership.application.port.out;

import com.minipay.membership.domain.Membership;

import java.util.Optional;

public interface TokenProvider {
    String createAccessToken(Membership.MembershipId membershipId);
    String createRefreshToken();
    void saveRefreshToken(String refreshToken, Membership.MembershipId membershipId);
    Optional<Membership.MembershipId> getMembershipIdByRefreshToken(String refreshToken);
    void deleteRefreshToken(String refreshToken);
}
