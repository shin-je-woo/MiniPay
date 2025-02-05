package com.minipay.membership.application.service;

import com.minipay.common.exception.DomainNotFoundException;
import com.minipay.common.annotation.Query;
import com.minipay.membership.application.port.in.GetMembershipQuery;
import com.minipay.membership.application.port.out.FindMembershipPort;
import com.minipay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Query
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetMembershipService implements GetMembershipQuery {

    private final FindMembershipPort findMembershipPort;

    @Override
    public Membership getMembership(Long membershipId) {
        return findMembershipPort.findMember(new Membership.MembershipId(membershipId))
                .orElseThrow(() -> new DomainNotFoundException("memberId: " + membershipId + " not found"));
    }
}
