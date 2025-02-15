package com.minipay.membership.adapter.out.persistence.adapter;

import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.membership.adapter.out.persistence.repository.SpringDataMembershipRepository;
import com.minipay.membership.adapter.out.persistence.entity.MembershipJpaEntity;
import com.minipay.membership.adapter.out.persistence.mapper.MembershipMapper;
import com.minipay.membership.application.port.out.CreateMembershipPort;
import com.minipay.membership.application.port.out.FindMembershipPort;
import com.minipay.membership.application.port.out.ModifyMembershipPort;
import com.minipay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements CreateMembershipPort, ModifyMembershipPort, FindMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership createMembership(Membership membership) {
        MembershipJpaEntity membershipJpaEntity = membershipMapper.mapToJpaEntity(membership);
        membershipRepository.save(membershipJpaEntity);

        return membershipMapper.mapToDomain(membershipJpaEntity);
    }

    @Override
    public Membership modifyMembership(Membership membership) {
        MembershipJpaEntity membershipJpaEntity = membershipMapper.mapToJpaEntity(membership);
        membershipRepository.save(membershipJpaEntity);

        return membershipMapper.mapToDomain(membershipJpaEntity);
    }

    @Override
    public Optional<Membership> findMember(Membership.MembershipId membershipId) {
        return membershipRepository.findByMembershipId(membershipId.value())
                .map(membershipMapper::mapToDomain);
    }
}
