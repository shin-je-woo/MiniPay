package com.minipay.banking.application.port.out;

import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.ExternalBankAccount;

import java.util.List;

public interface BankAccountPersistencePort {
    BankAccount createBankAccount(BankAccount bankAccount);
    BankAccount readBankAccount(BankAccount.BankAccountId bankAccountId);
    List<BankAccount> readBankAccountsByBankName(ExternalBankAccount.BankName bankName);
}
