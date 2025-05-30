package com.minipay.membership.adapter.out.persistence.adapter;

import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.common.exception.DataNotFoundException;
import com.minipay.membership.adapter.out.persistence.entity.MembershipJpaEntity;
import com.minipay.membership.adapter.out.persistence.mapper.MembershipMapper;
import com.minipay.membership.adapter.out.persistence.repository.SpringDataMembershipRepository;
import com.minipay.membership.application.port.out.MembershipPersistencePort;
import com.minipay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements MembershipPersistencePort {

    private final SpringDataMembershipRepository membershipRepository;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership createMembership(Membership membership) {
        MembershipJpaEntity membershipJpaEntity = membershipMapper.mapToJpaEntity(membership);
        membershipRepository.save(membershipJpaEntity);

        return membershipMapper.mapToDomain(membershipJpaEntity);
    }

    @Override
    public Membership updateMembership(Membership membership) {
        MembershipJpaEntity membershipJpaEntity = membershipMapper.mapToJpaEntity(membership);
        membershipRepository.save(membershipJpaEntity);

        return membershipMapper.mapToDomain(membershipJpaEntity);
    }

    @Override
    public Membership readMembership(Membership.MembershipId membershipId) {
        return membershipRepository.findByMembershipId(membershipId.value())
                .map(membershipMapper::mapToDomain)
                .orElseThrow(() -> new DataNotFoundException("Membership not found"));
    }

    @Override
    public Membership readMembershipByEmail(Membership.MembershipEmail email) {
        return membershipRepository.findByEmail(email.value())
                .map(membershipMapper::mapToDomain)
                .orElseThrow(() -> new DataNotFoundException("Membership not found"));
    }

    @Override
    public List<Membership> readMembershipByAddress(Membership.MembershipAddress address) {
        return membershipRepository.findByAddress(address.value()).stream()
                .map(membershipMapper::mapToDomain)
                .toList();
    }
}
