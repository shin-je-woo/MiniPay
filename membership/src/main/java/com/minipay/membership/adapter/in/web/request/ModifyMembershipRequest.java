package com.minipay.membership.adapter.in.web.request;

public record ModifyMembershipRequest(
        String name,
        String email,
        String address
) {
}
