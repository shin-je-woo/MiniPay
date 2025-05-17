package com.minipay.query.adapter.in.web.response;

import com.minipay.query.domain.MoneySumByMembership;

import java.math.BigDecimal;
import java.util.UUID;

public record MoneySumByMembershipResponse(
        UUID membershipId,
        String regionName,
        BigDecimal moneySum
) {
    public static MoneySumByMembershipResponse from(MoneySumByMembership moneySumByMembership) {
        return new MoneySumByMembershipResponse(
                moneySumByMembership.getMembershipId().value(),
                moneySumByMembership.getRegionName().value(),
                moneySumByMembership.getMoneySum().value()
        );
    }
}
