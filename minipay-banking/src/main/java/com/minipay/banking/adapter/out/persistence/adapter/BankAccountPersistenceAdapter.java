package com.minipay.banking.adapter.out.persistence.adapter;

import com.minipay.banking.adapter.out.persistence.entity.BankAccountJpaEntity;
import com.minipay.banking.adapter.out.persistence.mapper.BankAccountMapper;
import com.minipay.banking.adapter.out.persistence.repository.SpringDataBankAccountRepository;
import com.minipay.banking.application.port.out.BankAccountPersistencePort;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.ExternalBankAccount;
import com.minipay.common.annotation.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class BankAccountPersistenceAdapter implements BankAccountPersistencePort {

    private final SpringDataBankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        BankAccountJpaEntity bankAccountJpaEntity = bankAccountMapper.mapToJpaEntity(bankAccount);
        bankAccountRepository.save(bankAccountJpaEntity);

        return bankAccountMapper.mapToDomain(bankAccountJpaEntity);
    }

    @Override
    public BankAccount readBankAccount(BankAccount.BankAccountId bankAccountId) {
        return bankAccountRepository.findByBankAccountId(bankAccountId.value())
                .map(bankAccountMapper::mapToDomain)
                .orElseThrow(() -> new IllegalArgumentException("BankAccount not found"));
    }

    @Override
    public List<BankAccount> readBankAccountsByBankName(ExternalBankAccount.BankName bankName) {
        return bankAccountRepository.findAllByBankName(bankName.value()).stream()
                .map(bankAccountMapper::mapToDomain)
                .toList();
    }
}
