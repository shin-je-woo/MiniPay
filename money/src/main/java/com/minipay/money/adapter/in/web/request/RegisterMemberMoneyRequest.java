package com.minipay.money.adapter.in.web.request;

public record RegisterMemberMoneyRequest(
        Long membershipId,
        Long bankAccountId
) {
}
