package com.minipay.money.adapter.out.persistence.adapter;

import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.common.exception.DataNotFoundException;
import com.minipay.money.adapter.out.persistence.entity.MemberMoneyJpaEntity;
import com.minipay.money.adapter.out.persistence.mapper.MemberMoneyMapper;
import com.minipay.money.adapter.out.persistence.repository.SpringDataMemberMoneyRepository;
import com.minipay.money.application.port.out.MemberMoneyPersistencePort;
import com.minipay.money.domain.MemberMoney;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberMoneyPersistenceAdapter implements MemberMoneyPersistencePort {

    private final SpringDataMemberMoneyRepository memberMoneyRepository;
    private final MemberMoneyMapper memberMoneyMapper;

    @Override
    public MemberMoney createMemberMoney(MemberMoney memberMoney) {
        MemberMoneyJpaEntity memberMoneyJpaEntity = memberMoneyMapper.mapToJpaEntity(memberMoney);
        memberMoneyRepository.save(memberMoneyJpaEntity);

        return memberMoneyMapper.mapToDomain(memberMoneyJpaEntity);
    }

    @Override
    public MemberMoney readMemberMoney(MemberMoney.MemberMoneyId memberMoneyId) {
        MemberMoneyJpaEntity memberMoneyJpaEntity = memberMoneyRepository.findByMemberMoneyId(memberMoneyId.value())
                .orElseThrow(() -> new DataNotFoundException("Member money not found"));

        return memberMoneyMapper.mapToDomain(memberMoneyJpaEntity);
    }

    @Override
    public void updateMemberMoney(MemberMoney memberMoney) {
        MemberMoneyJpaEntity existingEntity = memberMoneyRepository.findByMemberMoneyId(memberMoney.getMemberMoneyId().value())
                .orElseThrow(() -> new DataNotFoundException("Member money not found"));

        MemberMoneyJpaEntity memberMoneyJpaEntity = memberMoneyMapper.mapToExistingJpaEntity(memberMoney, existingEntity.getId());
        memberMoneyRepository.save(memberMoneyJpaEntity);
    }

    @Override
    public MemberMoney readMemberMoneyByMembershipId(UUID membershipId) {
        MemberMoneyJpaEntity memberMoneyJpaEntity = memberMoneyRepository.findByMembershipId(membershipId)
                .orElseThrow(() -> new DataNotFoundException("Member money not found"));

        return memberMoneyMapper.mapToDomain(memberMoneyJpaEntity);
    }
}
