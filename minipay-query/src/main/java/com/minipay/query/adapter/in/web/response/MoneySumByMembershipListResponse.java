package com.minipay.query.adapter.in.web.response;

import com.minipay.query.domain.MoneySumByMembership;

import java.util.List;

public record MoneySumByMembershipListResponse(
        List<MoneySumByMembershipResponse> moneySumByMemberships
) {
    public static MoneySumByMembershipListResponse from(List<MoneySumByMembership> moneySumByMemberships) {
        List<MoneySumByMembershipResponse> responses = moneySumByMemberships.stream()
                .map(MoneySumByMembershipResponse::from)
                .toList();
        return new MoneySumByMembershipListResponse(responses);
    }
}
