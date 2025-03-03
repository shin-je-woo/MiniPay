package com.minipay.banking.application.port.out;

import com.minipay.banking.domain.BankAccount;

public interface BankAccountPersistencePort {
    BankAccount createBankAccount(BankAccount bankAccount);
    BankAccount getBankAccount(BankAccount.BankAccountId bankAccountId);
}
