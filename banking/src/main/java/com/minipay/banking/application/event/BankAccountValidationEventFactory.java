package com.minipay.banking.application.event;

import com.minipay.banking.application.port.out.ExternalBankAccountInfo;
import com.minipay.saga.command.CheckBankAccountCommand;
import com.minipay.saga.event.CheckBankAccountFailedEvent;
import com.minipay.saga.event.CheckBankAccountSucceededEvent;
import org.springframework.stereotype.Component;

@Component
public class BankAccountValidationEventFactory {

    public CheckBankAccountSucceededEvent successEvent(
            CheckBankAccountCommand command, ExternalBankAccountInfo externalBankInfo
    ) {
        return new CheckBankAccountSucceededEvent(
                command.checkBankAccountId(),
                command.rechargeRequestId(),
                command.bankAccountId(),
                command.memberMoneyId(),
                command.moneyHistoryId(),
                command.amount(),
                externalBankInfo.bankName(),
                externalBankInfo.accountNumber()
        );
    }

    public CheckBankAccountFailedEvent failureEvent(CheckBankAccountCommand command) {
        return new CheckBankAccountFailedEvent(
                command.checkBankAccountId(),
                command.rechargeRequestId(),
                command.bankAccountId(),
                command.memberMoneyId(),
                command.moneyHistoryId(),
                command.amount()
        );
    }
}
