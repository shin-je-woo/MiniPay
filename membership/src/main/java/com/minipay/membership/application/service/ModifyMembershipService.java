package com.minipay.membership.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.membership.application.port.in.ModifyMembershipCommand;
import com.minipay.membership.application.port.in.ModifyMembershipUseCase;
import com.minipay.membership.application.port.out.MembershipPersistencePort;
import com.minipay.membership.domain.Membership;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final MembershipPersistencePort membershipPersistencePort;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        Membership membership = membershipPersistencePort.readMembership(new Membership.MembershipId(command.getMembershipId()));
        Membership modifiedMembership = membership.changeInfo(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress())
        );

        return membershipPersistencePort.updateMembership(modifiedMembership);
    }
}
