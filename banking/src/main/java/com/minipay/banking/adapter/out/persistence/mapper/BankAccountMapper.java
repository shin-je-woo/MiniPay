package com.minipay.banking.adapter.out.persistence.mapper;

import com.minipay.banking.adapter.out.persistence.entity.BankAccountJpaEntity;
import com.minipay.banking.domain.BankAccount;
import com.minipay.common.annotation.DomainMapper;

@DomainMapper
public class BankAccountMapper {

    public BankAccount mapToDomain(BankAccountJpaEntity bankAccount) {
        return BankAccount.withId(
                new BankAccount.BankAccountId(bankAccount.getId()),
                new BankAccount.OwnerId(bankAccount.getOwnerId()),
                new BankAccount.LinkedBankAccount(
                        bankAccount.getBankName(),
                        bankAccount.getAccountNumber(),
                        bankAccount.getLinkedStatusIsValid()
                )
        );
    }

    public BankAccountJpaEntity mapToJpaEntity(BankAccount bankAccount) {
        return BankAccountJpaEntity.builder()
                .id(bankAccount.getBankAccountId() == null ? null : bankAccount.getBankAccountId().value())
                .ownerId(bankAccount.getOwnerId().value())
                .bankName(bankAccount.getLinkedBankAccount().bankName())
                .accountNumber(bankAccount.getLinkedBankAccount().accountNumber())
                .linkedStatusIsValid(bankAccount.getLinkedBankAccount().linkedStatusIsValid())
                .build();
    }
}
