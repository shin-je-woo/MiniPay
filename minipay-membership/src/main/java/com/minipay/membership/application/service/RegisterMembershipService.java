package com.minipay.membership.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.membership.application.port.in.RegisterMembershipCommand;
import com.minipay.membership.application.port.in.RegisterMembershipUseCase;
import com.minipay.membership.application.port.out.MembershipPersistencePort;
import com.minipay.membership.domain.Membership;
import com.minipay.membership.domain.PasswordManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final MembershipPersistencePort membershipPersistencePort;
    private final PasswordManager passwordManager;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        Membership.MembershipRawPassword membershipRawPassword = new Membership.MembershipRawPassword(command.getPassword());
        String hashedPassword = membershipRawPassword.hash(passwordManager);

        Membership membership = Membership.newInstance(
                command.isValid(),
                command.isCorp(),
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipPassword(hashedPassword),
                new Membership.MembershipAddress(command.getAddress())
        );

        return membershipPersistencePort.createMembership(membership);
    }
}
