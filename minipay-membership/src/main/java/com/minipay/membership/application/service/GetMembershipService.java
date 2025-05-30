package com.minipay.membership.application.service;

import com.minipay.common.annotation.Query;
import com.minipay.membership.application.port.in.GetMembershipUseCase;
import com.minipay.membership.application.port.out.MembershipPersistencePort;
import com.minipay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Query
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetMembershipService implements GetMembershipUseCase {

    private final MembershipPersistencePort membershipPersistencePort;

    @Override
    public Membership getMembership(UUID membershipId) {
        return membershipPersistencePort.readMembership(new Membership.MembershipId(membershipId));
    }

    @Override
    public List<Membership> getMembershipByAddress(String address) {
        return membershipPersistencePort.readMembershipByAddress(new Membership.MembershipAddress(address));
    }
}
