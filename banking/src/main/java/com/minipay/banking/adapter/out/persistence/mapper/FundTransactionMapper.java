package com.minipay.banking.adapter.out.persistence.mapper;

import com.minipay.banking.adapter.out.persistence.entity.FundTransactionJpaEntity;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.FundTransaction;
import com.minipay.banking.domain.model.Money;
import com.minipay.common.annotation.DomainMapper;

@DomainMapper
public class FundTransactionMapper {

    public FundTransaction mapToDomain(FundTransactionJpaEntity fundTransaction) {
        return FundTransaction.withId(
                new FundTransaction.FundTransactionId(fundTransaction.getFundTransactionId()),
                new BankAccount.BankAccountId(fundTransaction.getBankAccountId()),
                fundTransaction.getMinipayBankAccount(),
                fundTransaction.getFundType(),
                fundTransaction.getStatus(),
                new Money(fundTransaction.getAmount())
        );
    }

    public FundTransactionJpaEntity mapToJpaEntity(FundTransaction fundTransaction) {
        return FundTransactionJpaEntity.builder()
                .fundTransactionId(fundTransaction.getFundTransactionId().value())
                .bankAccountId(fundTransaction.getBankAccountId().value())
                .minipayBankAccount(fundTransaction.getMinipayBankAccount())
                .fundType(fundTransaction.getFundType())
                .status(fundTransaction.getStatus())
                .amount(fundTransaction.getAmount().value())
                .build();
    }
}
