package com.minipay.money.adapter.out.persistence.adapter;

import com.minipay.common.PersistenceAdapter;
import com.minipay.money.adapter.out.persistence.entity.MemberMoneyJpaEntity;
import com.minipay.money.adapter.out.persistence.mapper.MemberMoneyMapper;
import com.minipay.money.adapter.out.persistence.repository.SpringDataMemberMoneyRepository;
import com.minipay.money.application.port.out.CreateMemberMoneyPort;
import com.minipay.money.domain.MemberMoney;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberMoneyPersistenceAdapter implements CreateMemberMoneyPort {

    private final SpringDataMemberMoneyRepository memberMoneyRepository;
    private final MemberMoneyMapper memberMoneyMapper;

    @Override
    public MemberMoney createMemberMoney(MemberMoney memberMoney) {
        MemberMoneyJpaEntity memberMoneyJpaEntity = memberMoneyMapper.mapToJpaEntity(memberMoney);
        memberMoneyRepository.save(memberMoneyJpaEntity);

        return memberMoneyMapper.mapToDomain(memberMoneyJpaEntity);
    }
}
