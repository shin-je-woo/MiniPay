package com.minipay.membership.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.membership.application.port.in.RegisterMembershipCommand;
import com.minipay.membership.application.port.in.RegisterMembershipUseCase;
import com.minipay.membership.application.port.out.CreateMembershipPort;
import com.minipay.membership.domain.Membership;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final CreateMembershipPort createMembershipPort;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        Membership membership = Membership.create(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                command.isValid(),
                command.isCorp()
        );
        return createMembershipPort.createMembership(membership);
    }
}
