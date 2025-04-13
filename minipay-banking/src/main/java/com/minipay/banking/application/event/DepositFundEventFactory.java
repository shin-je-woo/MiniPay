package com.minipay.banking.application.event;

import com.minipay.saga.command.OrderDepositFundCommand;
import com.minipay.saga.event.OrderDepositFundFailedEvent;
import com.minipay.saga.event.OrderDepositFundSucceededEvent;
import org.springframework.stereotype.Component;

@Component
public class DepositFundEventFactory {

    public OrderDepositFundSucceededEvent successEvent(OrderDepositFundCommand command) {
        return new OrderDepositFundSucceededEvent(
                command.orderDepositFundId(),
                command.bankAccountId(),
                command.checkBankAccountId(),
                command.memberMoneyId(),
                command.moneyHistoryId(),
                command.amount(),
                command.bankName(),
                command.accountNumber()
        );
    }

    public OrderDepositFundFailedEvent failureEvent(OrderDepositFundCommand command) {
        return new OrderDepositFundFailedEvent(
                command.orderDepositFundId(),
                command.bankAccountId(),
                command.checkBankAccountId(),
                command.memberMoneyId(),
                command.moneyHistoryId(),
                command.amount(),
                command.bankName(),
                command.accountNumber()
        );
    }
}
