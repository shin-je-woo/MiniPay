package com.minipay.money.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.common.exception.BusinessException;
import com.minipay.money.application.port.in.*;
import com.minipay.money.application.port.out.MemberMoneyPersistencePort;
import com.minipay.money.application.port.out.MembershipServicePort;
import com.minipay.money.application.port.out.MoneyHistoryPersistencePort;
import com.minipay.money.domain.MemberMoney;
import com.minipay.money.domain.Money;
import com.minipay.money.domain.MoneyHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class IncreaseMoneyService implements IncreaseMoneyUseCase {

    private final MemberMoneyPersistencePort memberMoneyPersistencePort;
    private final MoneyHistoryPersistencePort moneyHistoryPersistencePort;
    private final MembershipServicePort membershipServicePort;

    @Override
    public MemberMoney increaseMoney(IncreaseMoneyCommand command) {
        MemberMoney memberMoney = memberMoneyPersistencePort.readMemberMoney(new MemberMoney.MemberMoneyId(command.getMemberMoneyId()));

        if (!membershipServicePort.isValidMembership(memberMoney.getMembershipId().value())) {
            throw new BusinessException("멤버쉽이 유효하지 않습니다.");
        }

        MoneyHistory moneyHistory = MoneyHistory.newInstance(
                memberMoney.getMemberMoneyId(),
                MoneyHistory.ChangeType.INCREASE,
                new Money(command.getAmount())
        );

        memberMoney.increaseBalance(new Money(command.getAmount()));
        memberMoneyPersistencePort.updateMemberMoney(memberMoney);

        moneyHistory.succeed(memberMoney);
        moneyHistoryPersistencePort.createMoneyHistory(moneyHistory);

        return memberMoney;
    }
}
