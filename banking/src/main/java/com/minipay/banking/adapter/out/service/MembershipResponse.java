package com.minipay.banking.adapter.out.service;

public record MembershipResponse(
        Long membershipId,
        String name,
        String email,
        String address,
        boolean isValid,
        boolean isCorp
) {
}
