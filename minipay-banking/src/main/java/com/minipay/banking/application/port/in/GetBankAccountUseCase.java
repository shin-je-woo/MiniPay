package com.minipay.banking.application.port.in;

import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.ExternalBankAccount;

import java.util.List;

public interface GetBankAccountUseCase {
    BankAccount getBankAccount(BankAccount.BankAccountId bankAccountId);
    List<BankAccount> getBankAccountsByBankName(ExternalBankAccount.BankName bankName);
}
