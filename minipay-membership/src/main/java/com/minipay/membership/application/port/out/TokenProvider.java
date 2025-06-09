package com.minipay.membership.application.port.out;

import java.util.UUID;

public interface TokenProvider {
    String createAccessToken(UUID membershipId);
    String createRefreshToken(UUID membershipId);
}
