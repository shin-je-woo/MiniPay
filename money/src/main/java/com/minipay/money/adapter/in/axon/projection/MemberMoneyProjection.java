package com.minipay.money.adapter.in.axon.projection;

import com.minipay.money.application.port.out.MemberMoneyPersistencePort;
import com.minipay.money.application.port.out.MoneyHistoryPersistencePort;
import com.minipay.money.domain.event.MemberMoneyCreatedEvent;
import com.minipay.money.domain.event.RechargeMoneyRequestedEvent;
import com.minipay.money.domain.model.MemberMoney;
import com.minipay.money.domain.model.Money;
import com.minipay.money.domain.model.MoneyHistory;
import com.minipay.saga.event.BankAccountCheckFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("memberMoney-projection")
public class MemberMoneyProjection {

    private final MemberMoneyPersistencePort memberMoneyPersistencePort;
    private final MoneyHistoryPersistencePort moneyHistoryPersistencePort;

    @EventHandler
    public void on(MemberMoneyCreatedEvent event) {
        log.info("[Projection] MemberMoney 저장");
        MemberMoney memberMoney = MemberMoney.withId(
                new MemberMoney.MemberMoneyId(event.memberMoneyId()),
                new MemberMoney.MembershipId(event.membershipId()),
                new MemberMoney.BankAccountId(event.bankAccountId()),
                new Money(event.balance())
        );
        memberMoneyPersistencePort.createMemberMoney(memberMoney);
    }

    @EventHandler
    public void on(RechargeMoneyRequestedEvent event) {
        log.info("[Projection] 충전 요청 이력 저장");
        MoneyHistory moneyHistory = MoneyHistory.newInstance(
                new MemberMoney.MemberMoneyId(event.memberMoneyId()),
                MoneyHistory.ChangeType.RECHARGE,
                new Money(event.amount())
        );
        MoneyHistory withIdHistory = MoneyHistory.withId(
                new MoneyHistory.MoneyHistoryId(event.moneyHistoryId()),
                moneyHistory.getMemberMoneyId(),
                moneyHistory.getChangeType(),
                moneyHistory.getAmount(),
                moneyHistory.getCreatedAt(),
                moneyHistory.getChangeStatus(),
                moneyHistory.getAfterBalance(),
                moneyHistory.getUpdatedAt()
        );
        moneyHistoryPersistencePort.createMoneyHistory(withIdHistory);
    }

    @EventHandler
    public void on(BankAccountCheckFailedEvent event) {
        log.info("[Projection] 충전 요청 실패 이력 저장, 사유: 유효하지 않은 계좌");
        MoneyHistory moneyHistory = moneyHistoryPersistencePort.readMoneyHistory(new MoneyHistory.MoneyHistoryId(event.moneyHistoryId()));
        moneyHistory.failNotValidAccount();
        moneyHistoryPersistencePort.updateMoneyHistory(moneyHistory);
    }
}
