package com.minipay.banking.adapter.out.persistence.mapper;

import com.minipay.banking.adapter.out.persistence.entity.BankAccountJpaEntity;
import com.minipay.banking.domain.BankAccount;
import com.minipay.banking.domain.ExternalBankAccount;
import com.minipay.common.annotation.DomainMapper;

@DomainMapper
public class BankAccountMapper {

    public BankAccount mapToDomain(BankAccountJpaEntity bankAccount) {
        return BankAccount.withId(
                new BankAccount.BankAccountId(bankAccount.getBankAccountId()),
                new BankAccount.MembershipId(bankAccount.getMembershipId()),
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(bankAccount.getBankName()),
                        new ExternalBankAccount.AccountNumber(bankAccount.getAccountNumber())
                ),
                bankAccount.getLinkedStatus()
        );
    }

    public BankAccountJpaEntity mapToJpaEntity(BankAccount bankAccount) {
        return BankAccountJpaEntity.builder()
                .bankAccountId(bankAccount.getBankAccountId().value())
                .membershipId(bankAccount.getMembershipId().value())
                .bankName(bankAccount.getLinkedBankAccount().bankName().value())
                .accountNumber(bankAccount.getLinkedBankAccount().accountNumber().value())
                .linkedStatus(bankAccount.getLinkedStatus())
                .build();
    }
}
