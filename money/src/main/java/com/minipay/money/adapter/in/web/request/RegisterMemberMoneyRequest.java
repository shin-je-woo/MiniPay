package com.minipay.money.adapter.in.web.request;

import java.util.UUID;

public record RegisterMemberMoneyRequest(
        UUID membershipId,
        UUID bankAccountId
) {
}
