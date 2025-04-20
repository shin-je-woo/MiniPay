package com.minipay.membership.adapter.in.web.response;

import com.minipay.membership.domain.Membership;

import java.util.List;

public record MembershipByAddressResponse(
        List<MembershipResponse> memberships
) {
    public static MembershipByAddressResponse from(List<Membership> memberships) {
        List<MembershipResponse> membershipResponses = memberships.stream()
                .map(MembershipResponse::from)
                .toList();
        return new MembershipByAddressResponse(membershipResponses);
    }
}
