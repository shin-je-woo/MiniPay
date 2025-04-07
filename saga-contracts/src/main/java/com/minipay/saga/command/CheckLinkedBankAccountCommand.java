package com.minipay.saga.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckLinkedBankAccountCommand(
        @TargetAggregateIdentifier UUID bankAccountId,
        UUID checkLinkedBankAccountId,
        UUID rechargeRequestId,
        UUID memberMoneyId,
        UUID moneyHistoryId,
        UUID membershipId,
        BigDecimal amount
) {
}
