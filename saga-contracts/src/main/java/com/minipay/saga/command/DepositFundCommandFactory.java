package com.minipay.saga.command;

import com.minipay.saga.event.DepositFundCreatedEvent;

public class DepositFundCommandFactory {

    public static SucceedDepositFundCommand successCommand(DepositFundCreatedEvent event) {
        return new SucceedDepositFundCommand(
                event.fundTransactionId(),
                event.orderDepositFundId(),
                event.bankAccountId(),
                event.checkBankAccountId(),
                event.memberMoneyId(),
                event.moneyHistoryId(),
                event.amount(),
                event.bankName(),
                event.accountNumber()
        );
    }

    public static FailDepositFundCommand failureCommand(DepositFundCreatedEvent event) {
        return new FailDepositFundCommand(
                event.fundTransactionId(),
                event.orderDepositFundId(),
                event.bankAccountId(),
                event.checkBankAccountId(),
                event.memberMoneyId(),
                event.moneyHistoryId(),
                event.amount(),
                event.bankName(),
                event.accountNumber()
        );
    }
}
