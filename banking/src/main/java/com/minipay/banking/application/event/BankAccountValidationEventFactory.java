package com.minipay.banking.application.event;

import com.minipay.saga.command.CheckLinkedBankAccountCommand;
import com.minipay.saga.event.BankAccountCheckFailedEvent;
import com.minipay.saga.event.BankAccountCheckSucceededEvent;
import org.springframework.stereotype.Component;

@Component
public class BankAccountValidationEventFactory {

    public BankAccountCheckSucceededEvent successEvent(CheckLinkedBankAccountCommand command) {
        return new BankAccountCheckSucceededEvent(
                command.checkLinkedBankAccountId(),
                command.rechargeRequestId(),
                command.bankAccountId(),
                command.memberMoneyId(),
                command.moneyHistoryId()
        );
    }

    public BankAccountCheckFailedEvent failureEvent(CheckLinkedBankAccountCommand command) {
        return new BankAccountCheckFailedEvent(
                command.checkLinkedBankAccountId(),
                command.rechargeRequestId(),
                command.bankAccountId(),
                command.memberMoneyId(),
                command.moneyHistoryId()
        );
    }
}
