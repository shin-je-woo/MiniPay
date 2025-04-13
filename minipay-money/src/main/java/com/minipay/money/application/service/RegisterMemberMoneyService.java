package com.minipay.money.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.common.exception.BusinessException;
import com.minipay.money.adapter.in.axon.command.CreateMemberMoneyCommand;
import com.minipay.money.application.port.in.RegisterMemberMoneyCommand;
import com.minipay.money.application.port.in.RegisterMemberMoneyUseCase;
import com.minipay.money.application.port.out.MemberMoneyPersistencePort;
import com.minipay.money.application.port.out.MembershipServicePort;
import com.minipay.money.domain.model.MemberMoney;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterMemberMoneyService implements RegisterMemberMoneyUseCase {

    private final MembershipServicePort membershipServicePort;
    private final MemberMoneyPersistencePort memberMoneyPersistencePort;
    private final CommandGateway commandGateway;

    @Override
    public MemberMoney registerMemberMoney(RegisterMemberMoneyCommand command) {
        MemberMoney memberMoney = MemberMoney.newInstance(
                new MemberMoney.MembershipId(command.getMembershipId()),
                new MemberMoney.BankAccountId(command.getBankAccountId())
        );
        return memberMoneyPersistencePort.createMemberMoney(memberMoney);
    }

    @Override
    public void registerMemberMoneyByAxon(RegisterMemberMoneyCommand command) {
        if (!membershipServicePort.isValidMembership(command.getMembershipId())) {
            throw new BusinessException("Invalid membership ID");
        }
        commandGateway.send(new CreateMemberMoneyCommand(
                command.getMembershipId(),
                command.getBankAccountId()
        ));
    }
}
