package com.minipay.banking.adapter.out.persistence.adapter;

import com.minipay.banking.adapter.out.persistence.entity.BankAccountJpaEntity;
import com.minipay.banking.adapter.out.persistence.mapper.BankAccountMapper;
import com.minipay.banking.adapter.out.persistence.repository.SpringDataBankAccountRepository;
import com.minipay.banking.application.port.out.CreateBankAccountPort;
import com.minipay.banking.domain.BankAccount;
import com.minipay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class BankAccountPersistenceAdapter implements CreateBankAccountPort {

    private final SpringDataBankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        BankAccountJpaEntity bankAccountJpaEntity = bankAccountMapper.mapToJpaEntity(bankAccount);
        bankAccountRepository.save(bankAccountJpaEntity);

        return bankAccountMapper.mapToDomain(bankAccountJpaEntity);
    }
}
