package com.minipay.membership.adapter.out.persistence;

import com.minipay.membership.application.port.out.RegisterMembershipPort;
import com.minipay.membership.domain.Membership;
import common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership createMembership(
            Membership.MembershipName membershipName,
            Membership.MembershipEmail membershipEmail,
            Membership.MembershipAddress membershipAddress,
            Membership.MembershipIsValid membershipIsValid,
            Membership.MembershipIsCorp membershipIsCorp
    ) {
        MembershipJpaEntity membershipJpaEntity = MembershipJpaEntity.builder()
                .name(membershipName.name())
                .email(membershipEmail.email())
                .address(membershipAddress.address())
                .isValid(membershipIsValid.isValid())
                .isCorp(membershipIsCorp.isCorp())
                .build();
        membershipRepository.save(membershipJpaEntity);

        return membershipMapper.mapToDomain(membershipJpaEntity);
    }
}
