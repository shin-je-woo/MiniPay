package com.minipay.membership.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.common.exception.BusinessException;
import com.minipay.membership.application.port.in.LoginMembershipCommand;
import com.minipay.membership.application.port.in.LoginMembershipUseCase;
import com.minipay.membership.application.port.out.MembershipPersistencePort;
import com.minipay.membership.application.port.out.TokenProvider;
import com.minipay.membership.domain.Membership;
import com.minipay.membership.domain.PasswordManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class LoginMembershipService implements LoginMembershipUseCase {

    private final MembershipPersistencePort membershipPersistencePort;
    private final PasswordManager passwordManager;
    private final TokenProvider tokenProvider;

    @Override
    public String login(LoginMembershipCommand command) {
        Membership membership = membershipPersistencePort.readMembershipByEmail(new Membership.MembershipEmail(command.getEmail()));
        Membership.MembershipRawPassword rawPassword = new Membership.MembershipRawPassword(command.getPassword());

        if (!membership.getPassword().matches(rawPassword, passwordManager)) {
            throw new BusinessException("Invalid password for membership with email: " + command.getEmail());
        }

        return tokenProvider.createAccessToken(membership.getMembershipId().value());
    }
}
