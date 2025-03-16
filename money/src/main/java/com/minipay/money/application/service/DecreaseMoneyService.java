package com.minipay.money.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.common.exception.BusinessException;
import com.minipay.money.application.port.in.DecreaseMoneyAfterBankingCommand;
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

import java.util.UUID;

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
        return executeDecreaseMoney(memberMoney, new Money(command.getAmount()));
    }

    @Override
    public void decreaseMoneyAfterBanking(DecreaseMoneyAfterBankingCommand command) {
        UUID bankAccountId = command.getBankAccountId();
        MemberMoney memberMoney = memberMoneyPersistencePort.readMemberMoneyByBankAccountId(bankAccountId);
        executeDecreaseMoney(memberMoney, new Money(command.getAmount()));
    }

    private MemberMoney executeDecreaseMoney(MemberMoney memberMoney, Money decreaseAmount) {
        if (!membershipServicePort.isValidMembership(memberMoney.getMembershipId().value())) {
            throw new BusinessException("멤버쉽이 유효하지 않습니다.");
        }

        MoneyHistory moneyHistory = MoneyHistory.newInstance(
                memberMoney.getMemberMoneyId(),
                MoneyHistory.ChangeType.DECREASE,
                decreaseAmount
        );

        memberMoney.decreaseBalance(decreaseAmount);
        memberMoneyPersistencePort.updateMemberMoney(memberMoney);

        moneyHistory.succeed(memberMoney);
        moneyHistoryPersistencePort.createMoneyHistory(moneyHistory);

        return memberMoney;
    }
}
