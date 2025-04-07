package com.minipay.banking.application.port.in;

import com.minipay.saga.command.CheckLinkedBankAccountCommand;

public interface BankAccountValidateUseCase {
    void validateLinkedAccount(CheckLinkedBankAccountCommand command);
}
