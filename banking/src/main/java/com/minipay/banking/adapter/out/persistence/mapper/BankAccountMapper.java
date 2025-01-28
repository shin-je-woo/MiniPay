package com.minipay.banking.adapter.out.persistence.mapper;

import com.minipay.banking.adapter.out.persistence.entity.BankAccountJpaEntity;
import com.minipay.banking.domain.BankAccount;
import com.minipay.common.DomainMapper;

@DomainMapper
public class BankAccountMapper {

    public BankAccount mapToDomain(BankAccountJpaEntity bankAccount) {
        return BankAccount.withId(
                new BankAccount.BankAccountId(bankAccount.getId()),
                new BankAccount.OwnerId(bankAccount.getOwnerId()),
                bankAccount.getBankName(),
                bankAccount.getAccountNumber(),
                bankAccount.getLinkedStatusIsValid()
        );
    }

    public BankAccountJpaEntity mapToJpaEntity(BankAccount bankAccount) {
        return BankAccountJpaEntity.builder()
                .ownerId(bankAccount.getOwnerId().value())
                .bankName(bankAccount.getBankName())
                .accountNumber(bankAccount.getAccountNumber())
                .linkedStatusIsValid(bankAccount.isLinkedStatusIsValid())
                .build();
    }
}
