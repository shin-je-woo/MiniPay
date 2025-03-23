package com.minipay.money.adapter.axon.command;

import java.util.UUID;

public record CreateMemberMoneyCommand(
        UUID membershipId,
        UUID bankAccountId
) {
}
