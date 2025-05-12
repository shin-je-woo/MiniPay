package com.minipay.query.application.port.out;

import java.util.UUID;

public record MembershipInfo(
        UUID membershipId,
        String address
) {
}
