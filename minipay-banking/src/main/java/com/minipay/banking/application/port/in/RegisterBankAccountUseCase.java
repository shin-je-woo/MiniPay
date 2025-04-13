package com.minipay.banking.application.port.in;

import com.minipay.banking.domain.model.BankAccount;

public interface RegisterBankAccountUseCase {
     BankAccount registerBankAccount(RegisterBankAccountCommand command);
     void registerBankAccountByAxon(RegisterBankAccountCommand command);
}
