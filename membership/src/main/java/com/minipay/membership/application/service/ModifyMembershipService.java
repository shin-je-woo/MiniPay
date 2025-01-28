package com.minipay.membership.application.service;

import com.minipay.common.DomainNotFoundException;
import com.minipay.common.UseCase;
import com.minipay.membership.application.port.in.ModifyMembershipCommand;
import com.minipay.membership.application.port.in.ModifyMembershipUseCase;
import com.minipay.membership.application.port.out.FindMembershipPort;
import com.minipay.membership.application.port.out.ModifyMembershipPort;
import com.minipay.membership.domain.Membership;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final FindMembershipPort findMembershipPort;
    private final ModifyMembershipPort modifyMembershipPort;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        Membership membership = findMembershipPort.findMember(new Membership.MembershipId(command.getMembershipId()))
                .orElseThrow(() -> new DomainNotFoundException("Membership not found"));
        Membership modifiedMembership = membership.changeInfo(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress())
        );

        return modifyMembershipPort.modifyMembership(modifiedMembership);
    }
}
