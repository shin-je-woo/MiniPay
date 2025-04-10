package com.minipay.saga.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckBankAccountCommand(
        @TargetAggregateIdentifier UUID bankAccountId,
        UUID checkBankAccountId,
        UUID rechargeRequestId,
        UUID memberMoneyId,
        UUID moneyHistoryId,
        UUID membershipId,
        BigDecimal amount
) {
}
