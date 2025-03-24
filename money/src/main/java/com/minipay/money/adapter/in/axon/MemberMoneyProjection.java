package com.minipay.money.adapter.in.axon;

import com.minipay.money.application.port.out.MemberMoneyPersistencePort;
import com.minipay.money.domain.event.MemberMoneyCreatedEvent;
import com.minipay.money.domain.model.MemberMoney;
import com.minipay.money.domain.model.Money;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("memberMoney-projection")
public class MemberMoneyProjection {

    private final MemberMoneyPersistencePort memberMoneyPersistencePort;

    @EventHandler
    public void on(MemberMoneyCreatedEvent event) {
        MemberMoney memberMoney = MemberMoney.withId(
                new MemberMoney.MemberMoneyId(event.memberMoneyId()),
                new MemberMoney.MembershipId(event.membershipId()),
                new MemberMoney.BankAccountId(event.bankAccountId()),
                new Money(event.balance())
        );
        memberMoneyPersistencePort.createMemberMoney(memberMoney);
    }
}
