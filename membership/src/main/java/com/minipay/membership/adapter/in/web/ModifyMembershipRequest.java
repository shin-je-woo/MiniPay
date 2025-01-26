package com.minipay.membership.adapter.in.web;

public record ModifyMembershipRequest(
        String name,
        String email,
        String address,
        Boolean isValid,
        Boolean isCorp
) {
}
