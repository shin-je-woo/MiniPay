package com.minipay.money.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.money.application.port.in.RegisterMemberMoneyCommand;
import com.minipay.money.application.port.in.RegisterMemberMoneyUseCase;
import com.minipay.money.application.port.out.MemberMoneyPersistencePort;
import com.minipay.money.domain.MemberMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterMemberMoneyService implements RegisterMemberMoneyUseCase {

    private final MemberMoneyPersistencePort memberMoneyPersistencePort;

    @Override
    public MemberMoney registerMemberMoney(RegisterMemberMoneyCommand command) {
        MemberMoney memberMoney = MemberMoney.newInstance(
                new MemberMoney.MembershipId(command.getMembershipId()),
                new MemberMoney.BankAccountId(command.getBankAccountId())
        );
        return memberMoneyPersistencePort.createMemberMoney(memberMoney);
    }
}
