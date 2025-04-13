package com.minipay.membership.adapter.in.web.response;

import com.minipay.membership.domain.Membership;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
public record MembershipResponse(
        UUID membershipId,
        String name,
        String email,
        String address,
        boolean isValid,
        boolean isCorp
) {
    public static MembershipResponse from(Membership membership) {
        return MembershipResponse.builder()
                .membershipId(membership.getMembershipId().value())
                .name(membership.getName().value())
                .email(membership.getEmail().value())
                .address(membership.getAddress().value())
                .isValid(membership.isValid())
                .isCorp(membership.isCorp())
                .build();
    }
}
