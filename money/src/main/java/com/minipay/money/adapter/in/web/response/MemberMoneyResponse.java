package com.minipay.money.adapter.in.web.response;

import com.minipay.money.domain.MemberMoney;

import java.math.BigDecimal;
import java.util.UUID;

public record MemberMoneyResponse(
        UUID memberMoneyId,
        UUID membershipId,
        UUID bankAccountId,
        BigDecimal balance
) {
    public static MemberMoneyResponse from(MemberMoney memberMoney) {
        return new MemberMoneyResponse(
                memberMoney.getMemberMoneyId().value(),
                memberMoney.getMembershipId().value(),
                memberMoney.getBankAccountId().value(),
                memberMoney.getBalance().value()
        );
    }
}
