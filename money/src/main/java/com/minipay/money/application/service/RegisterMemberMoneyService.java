package com.minipay.money.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.money.adapter.axon.command.CreateMemberMoneyCommand;
import com.minipay.money.application.port.in.RegisterMemberMoneyCommand;
import com.minipay.money.application.port.in.RegisterMemberMoneyUseCase;
import com.minipay.money.application.port.out.MemberMoneyPersistencePort;
import com.minipay.money.domain.MemberMoney;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterMemberMoneyService implements RegisterMemberMoneyUseCase {

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
    public void registerMemberMoneyAxon(RegisterMemberMoneyCommand command) {
        CreateMemberMoneyCommand axonCommand = new CreateMemberMoneyCommand(command.getMembershipId(), command.getBankAccountId());
        commandGateway.send(axonCommand)
                .whenComplete((result, throwable) -> {
                            if (throwable != null) {
                                log.error("Failed to send command", throwable);
                                throw new RuntimeException(throwable);
                            }
                            log.info("Command sent successfully {}", result);
                        }
                );
    }
}
