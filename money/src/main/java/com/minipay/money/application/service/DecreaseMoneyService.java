package com.minipay.money.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.common.exception.BusinessException;
import com.minipay.money.application.port.in.DecreaseMoneyCommand;
import com.minipay.money.application.port.in.DecreaseMoneyUseCase;
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
public class DecreaseMoneyService implements DecreaseMoneyUseCase {

    private final MemberMoneyPersistencePort memberMoneyPersistencePort;
    private final MoneyHistoryPersistencePort moneyHistoryPersistencePort;
    private final MembershipServicePort membershipServicePort;

    @Override
    public MemberMoney decreaseMoney(DecreaseMoneyCommand command) {
        MemberMoney memberMoney = memberMoneyPersistencePort.readMemberMoney(new MemberMoney.MemberMoneyId(command.getMemberMoneyId()));

        if (!membershipServicePort.isValidMembership(memberMoney.getMembershipId().value())) {
            throw new BusinessException("멤버쉽이 유효하지 않습니다.");
        }

        MoneyHistory moneyHistory = MoneyHistory.newInstance(
                memberMoney.getMemberMoneyId(),
                MoneyHistory.ChangeType.DECREASE,
                new Money(command.getAmount())
        );

        MemberMoney decreasedMemberMoney = memberMoney.decreaseBalance(new Money(command.getAmount()));
        memberMoneyPersistencePort.updateMemberMoney(decreasedMemberMoney);

        moneyHistory.succeed(decreasedMemberMoney);
        moneyHistoryPersistencePort.createMoneyHistory(moneyHistory);

        return decreasedMemberMoney;
    }
}
