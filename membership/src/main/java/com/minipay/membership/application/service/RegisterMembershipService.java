package com.minipay.membership.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.membership.application.port.in.RegisterMembershipCommand;
import com.minipay.membership.application.port.in.RegisterMembershipUseCase;
import com.minipay.membership.application.port.out.MembershipPersistencePort;
import com.minipay.membership.domain.Membership;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final MembershipPersistencePort membershipPersistencePort;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        Membership membership = Membership.newInstance(
                command.isValid(),
                command.isCorp(),
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress())
        );

        return membershipPersistencePort.createMembership(membership);
    }
}
