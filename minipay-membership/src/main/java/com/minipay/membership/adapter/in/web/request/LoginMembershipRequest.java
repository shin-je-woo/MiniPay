package com.minipay.membership.adapter.in.web.request;

public record LoginMembershipRequest(
        String email,
        String password
) {
}
