package com.minipay.banking.application.service;

import com.minipay.banking.application.port.in.GetBankAccountUseCase;
import com.minipay.banking.application.port.out.BankAccountPersistencePort;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.ExternalBankAccount;
import com.minipay.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetBankAccountService implements GetBankAccountUseCase {

    private final BankAccountPersistencePort bankAccountPersistencePort;

    @Override
    public BankAccount getBankAccount(BankAccount.BankAccountId bankAccountId) {
        return bankAccountPersistencePort.readBankAccount(bankAccountId);
    }

    @Override
    public List<BankAccount> getBankAccountsByBankName(ExternalBankAccount.BankName bankName) {
        return bankAccountPersistencePort.readBankAccountsByBankName(bankName);
    }
}
