package com.minipay.banking.adapter.in.axon.commnad;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record SucceedWithdrawalFundCommand(
        @TargetAggregateIdentifier UUID fundTransactionId
) {
}
