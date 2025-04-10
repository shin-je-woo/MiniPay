package com.minipay.banking.application.port.in;

import com.minipay.saga.command.CheckBankAccountCommand;

public interface BankAccountValidateUseCase {
    void validateLinkedAccount(CheckBankAccountCommand command);
}
