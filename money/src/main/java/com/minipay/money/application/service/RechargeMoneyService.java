package com.minipay.money.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.common.exception.BusinessException;
import com.minipay.money.application.port.in.RechargeMoneyCommand;
import com.minipay.money.application.port.in.RechargeMoneyUseCase;
import com.minipay.money.application.port.in.RequestMoneyRechargeCommand;
import com.minipay.money.application.port.out.*;
import com.minipay.money.domain.MemberMoney;
import com.minipay.money.domain.Money;
import com.minipay.money.domain.MoneyHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RechargeMoneyService implements RechargeMoneyUseCase {

    private final MemberMoneyPersistencePort memberMoneyPersistencePort;
    private final MoneyHistoryPersistencePort moneyHistoryPersistencePort;
    private final MembershipServicePort membershipServicePort;

    @Override
    public MemberMoney requestMoneyRecharge(RequestMoneyRechargeCommand command) {
        MemberMoney memberMoney = memberMoneyPersistencePort.readMemberMoney(new MemberMoney.MemberMoneyId(command.getMemberMoneyId()));

        // 1. 고객 정보가 정상인지 확인 [멤버 서비스]
        if (!membershipServicePort.isValidMembership(memberMoney.getMembershipId().value())) {
            throw new BusinessException("멤버쉽이 유효하지 않습니다.");
        }

        // 2. 요청 이력 저장 및 충전 요청 이벤트 발행
        MoneyHistory moneyHistory = memberMoney.requestRechargeMoney(new Money(command.getAmount()));
        moneyHistoryPersistencePort.createMoneyHistory(moneyHistory);

        return memberMoney;
    }

    @Override
    public void rechargeMoney(RechargeMoneyCommand command) {
        MoneyHistory moneyHistory = moneyHistoryPersistencePort.readMoneyHistory(new MoneyHistory.MoneyHistoryId(command.getMoneyHistoryId()));
        Money rechargeMoneyAmount = moneyHistory.getAmount();

        MemberMoney memberMoney = memberMoneyPersistencePort.readMemberMoney(moneyHistory.getMemberMoneyId());
        memberMoney.increaseBalance(rechargeMoneyAmount);
        memberMoneyPersistencePort.updateMemberMoney(memberMoney);

        moneyHistory.succeed(memberMoney);
        moneyHistoryPersistencePort.updateMoneyHistory(moneyHistory);
    }
}
