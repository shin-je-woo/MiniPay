package com.minipay.money.adapter.in.axon.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record CheckLinkedBankAccountCommand(
        @TargetAggregateIdentifier UUID bankAccountId,
        UUID rechargeRequestId,
        UUID membershipId
) {
}
