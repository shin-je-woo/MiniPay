package com.minipay.membership.application.service;

import com.minipay.membership.application.port.in.GetMembershipQuery;
import com.minipay.membership.application.port.out.FindMembershipPort;
import com.minipay.membership.domain.Membership;
import common.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Query
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetMembershipService implements GetMembershipQuery {
    private final FindMembershipPort findMembershipPort;

    @Override
    public Membership getMembership(String memberId) {
        return findMembershipPort.findMember(new Membership.MembershipId(memberId))
                .orElseThrow(() -> new IllegalArgumentException("memberId: " + memberId + " not found"));
    }
}
