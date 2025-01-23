package com.minipay.membership.adapter.in.web;

public record RegisterMembershipRequest(
        String name,
        String email,
        String address,
        Boolean isCorp
) {
}
