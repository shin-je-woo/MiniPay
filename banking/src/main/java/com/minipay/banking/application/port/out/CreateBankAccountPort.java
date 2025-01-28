package com.minipay.banking.application.port.out;

import com.minipay.banking.domain.BankAccount;

public interface CreateBankAccountPort {
    BankAccount createBankAccount(BankAccount bankAccount);
}
