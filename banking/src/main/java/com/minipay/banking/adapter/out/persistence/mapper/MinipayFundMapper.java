package com.minipay.banking.adapter.out.persistence.mapper;

import com.minipay.banking.adapter.out.persistence.entity.MinipayFundJpaEntity;
import com.minipay.banking.domain.BankAccount;
import com.minipay.banking.domain.MinipayFund;
import com.minipay.banking.domain.Money;
import com.minipay.common.annotation.DomainMapper;

@DomainMapper
public class MinipayFundMapper {

    public MinipayFund mapToDomain(MinipayFundJpaEntity minipayFund) {
        return MinipayFund.withId(
                new MinipayFund.MinipayFundId(minipayFund.getMinipayFundId()),
                new BankAccount.BankAccountId(minipayFund.getBankAccountId()),
                minipayFund.getMinipayBankAccount(),
                minipayFund.getFundType(),
                new Money(minipayFund.getAmount())
        );
    }

    public MinipayFundJpaEntity mapToJpaEntity(MinipayFund minipayFund) {
        return MinipayFundJpaEntity.builder()
                .minipayFundId(minipayFund.getMinipayFundId().value())
                .bankAccountId(minipayFund.getBankAccountId().value())
                .minipayBankAccount(minipayFund.getMinipayBankAccount())
                .fundType(minipayFund.getFundType())
                .amount(minipayFund.getAmount().value())
                .build();
    }
}
