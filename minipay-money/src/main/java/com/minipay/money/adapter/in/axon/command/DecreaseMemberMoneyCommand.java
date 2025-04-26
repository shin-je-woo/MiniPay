package com.minipay.money.adapter.in.axon.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

public record DecreaseMemberMoneyCommand(
        @TargetAggregateIdentifier UUID memberMoneyId,
        BigDecimal amount
) {
}
