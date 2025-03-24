package com.minipay.money.domain.aggregate;

import com.minipay.money.application.port.in.RegisterMemberMoneyCommand;
import com.minipay.money.domain.event.MemberMoneyCreatedEvent;
import com.minipay.money.domain.model.MemberMoney;
import com.minipay.money.domain.model.Money;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Slf4j
@Aggregate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMoneyAggregate {

    @AggregateIdentifier
    private UUID memberMoneyId;

    // 도메인 모델
    private MemberMoney memberMoney;

    // Command Handlers
    @CommandHandler
    public MemberMoneyAggregate(RegisterMemberMoneyCommand command) {
        log.info("CreateMemberMoneyCommand Handler");
        this.memberMoney = MemberMoney.newInstance(
                new MemberMoney.MembershipId(command.getMembershipId()),
                new MemberMoney.BankAccountId(command.getBankAccountId())
        );

        AggregateLifecycle.apply(new MemberMoneyCreatedEvent(
                memberMoney.getMemberMoneyId().value(),
                memberMoney.getMembershipId().value(),
                memberMoney.getBankAccountId().value(),
                memberMoney.getBalance().value()
        ));
    }

    // Event Sourcing Handlers
    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) {
        log.info("MemberMoneyCreatedEvent Handler");
        this.memberMoneyId = event.memberMoneyId();
        this.memberMoney = MemberMoney.withId(
                new MemberMoney.MemberMoneyId(event.memberMoneyId()),
                new MemberMoney.MembershipId(event.membershipId()),
                new MemberMoney.BankAccountId(event.bankAccountId()),
                new Money(event.balance())
        );
    }
}
