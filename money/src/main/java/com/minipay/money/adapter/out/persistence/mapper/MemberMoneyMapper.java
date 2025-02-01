package com.minipay.money.adapter.out.persistence.mapper;

import com.minipay.common.DomainMapper;
import com.minipay.money.adapter.out.persistence.entity.MemberMoneyJpaEntity;
import com.minipay.money.domain.MemberMoney;
import com.minipay.money.domain.Money;

@DomainMapper
public class MemberMoneyMapper {

    public MemberMoney mapToDomain(MemberMoneyJpaEntity memberMoney) {
        return MemberMoney.withId(
                new MemberMoney.MemberMoneyId(memberMoney.getId()),
                new MemberMoney.MembershipId(memberMoney.getMembershipId()),
                new MemberMoney.BankAccountId(memberMoney.getBankAccountId()),
                new Money(memberMoney.getBalance())
        );
    }

    public MemberMoneyJpaEntity mapToJpaEntity(MemberMoney memberMoney) {
        return MemberMoneyJpaEntity.builder()
                .id(memberMoney.getMemberMoneyId() == null ? null : memberMoney.getMemberMoneyId().value())
                .membershipId(memberMoney.getMembershipId().value())
                .bankAccountId(memberMoney.getBankAccountId().value())
                .balance(memberMoney.getBalance().value())
                .build();
    }
}
