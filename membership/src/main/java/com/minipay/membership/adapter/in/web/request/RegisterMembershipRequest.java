package com.minipay.membership.adapter.in.web.request;

public record RegisterMembershipRequest(
        String name,
        String email,
        String address,
        Boolean isCorp
) {
}
