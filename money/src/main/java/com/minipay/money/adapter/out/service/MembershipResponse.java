package com.minipay.money.adapter.out.service;

import java.util.UUID;

public record MembershipResponse(
        UUID membershipId,
        String name,
        String email,
        String address,
        boolean isValid,
        boolean isCorp
) {
}
