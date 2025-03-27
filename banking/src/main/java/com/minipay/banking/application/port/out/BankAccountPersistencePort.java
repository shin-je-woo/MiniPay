package com.minipay.banking.application.port.out;

import com.minipay.banking.domain.model.BankAccount;

public interface BankAccountPersistencePort {
    BankAccount createBankAccount(BankAccount bankAccount);
    BankAccount readBankAccount(BankAccount.BankAccountId bankAccountId);
}
