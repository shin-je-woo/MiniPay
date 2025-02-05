package com.minipay.money.adapter.out.persistence.adapter;

import com.minipay.common.exception.DataNotFoundException;
import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.money.adapter.out.persistence.entity.MemberMoneyJpaEntity;
import com.minipay.money.adapter.out.persistence.mapper.MemberMoneyMapper;
import com.minipay.money.adapter.out.persistence.repository.SpringDataMemberMoneyRepository;
import com.minipay.money.application.port.out.CreateMemberMoneyPort;
import com.minipay.money.application.port.out.LoadMemberMoneyPort;
import com.minipay.money.application.port.out.UpdateMemberMoneyPort;
import com.minipay.money.domain.MemberMoney;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberMoneyPersistenceAdapter implements
        CreateMemberMoneyPort, LoadMemberMoneyPort, UpdateMemberMoneyPort {

    private final SpringDataMemberMoneyRepository memberMoneyRepository;
    private final MemberMoneyMapper memberMoneyMapper;

    @Override
    public MemberMoney createMemberMoney(MemberMoney memberMoney) {
        MemberMoneyJpaEntity memberMoneyJpaEntity = memberMoneyMapper.mapToJpaEntity(memberMoney);
        memberMoneyRepository.save(memberMoneyJpaEntity);

        return memberMoneyMapper.mapToDomain(memberMoneyJpaEntity);
    }

    @Override
    public MemberMoney loadMemberMoney(MemberMoney.MemberMoneyId memberMoneyId) {
        MemberMoneyJpaEntity memberMoneyJpaEntity = memberMoneyRepository.findById(memberMoneyId.value())
                .orElseThrow(() -> new DataNotFoundException("Member money not found"));

        return memberMoneyMapper.mapToDomain(memberMoneyJpaEntity);
    }

    @Override
    public MemberMoney updateMemberMoney(MemberMoney memberMoney) {
        MemberMoneyJpaEntity memberMoneyJpaEntity = memberMoneyMapper.mapToJpaEntity(memberMoney);
        memberMoneyRepository.save(memberMoneyJpaEntity);

        return memberMoneyMapper.mapToDomain(memberMoneyJpaEntity);
    }
}
