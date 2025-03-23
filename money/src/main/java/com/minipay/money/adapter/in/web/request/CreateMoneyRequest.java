package com.minipay.money.adapter.in.web.request;

import java.util.UUID;

public record CreateMoneyRequest(
        UUID membershipId,
        UUID bankAccountId
) {
}
