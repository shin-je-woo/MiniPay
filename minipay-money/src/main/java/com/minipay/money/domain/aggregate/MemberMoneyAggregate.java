package com.minipay.money.domain.aggregate;

import com.minipay.common.exception.DomainRuleException;
import com.minipay.money.adapter.in.axon.command.CreateMemberMoneyCommand;
import com.minipay.money.adapter.in.axon.command.DecreaseMemberMoneyCommand;
import com.minipay.money.adapter.in.axon.command.IncreaseMemberMoneyCommand;
import com.minipay.money.adapter.in.axon.command.RequestRechargeMoneyCommand;
import com.minipay.money.domain.event.MemberMoneyCreatedEvent;
import com.minipay.money.domain.event.MemberMoneyDecreasedEvent;
import com.minipay.money.domain.event.MemberMoneyIncreasedEvent;
import com.minipay.money.domain.event.RechargeMoneyRequestedEvent;
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

    private MemberMoney memberMoney;

    @CommandHandler
    public MemberMoneyAggregate(CreateMemberMoneyCommand command) {
        log.info("CreateMemberMoneyCommand Handler");
        this.memberMoney = MemberMoney.newInstance(
                new MemberMoney.MembershipId(command.membershipId()),
                new MemberMoney.BankAccountId(command.bankAccountId())
        );

        AggregateLifecycle.apply(new MemberMoneyCreatedEvent(
                memberMoney.getMemberMoneyId().value(),
                memberMoney.getMembershipId().value(),
                memberMoney.getBankAccountId().value(),
                memberMoney.getBalance().value()
        ));
    }

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

    @CommandHandler
    public void on(RequestRechargeMoneyCommand command) {
        log.info("RequestRechargeMoneyCommand Handler");
        // Saga Start
        UUID moneyHistoryId = UUID.randomUUID();
        AggregateLifecycle.apply(new RechargeMoneyRequestedEvent(
                command.rechargeRequestId(),
                command.memberMoneyId(),
                moneyHistoryId,
                command.membershipId(),
                command.bankAccountId(),
                command.amount()
        ));
    }

    @CommandHandler
    public void on(IncreaseMemberMoneyCommand command) {
        log.info("IncreaseMemberMoneyCommand Handler");
        AggregateLifecycle.apply(new MemberMoneyIncreasedEvent(
                command.memberMoneyId(),
                command.amount()
        ));
    }

    @EventSourcingHandler
    public void on(MemberMoneyIncreasedEvent event) {
        log.info("MemberMoneyIncreasedEvent Handler");
        this.memberMoney.increaseBalance(new Money(event.amount()));
    }

    @CommandHandler
    public void on(DecreaseMemberMoneyCommand command) {
        log.info("DecreaseMemberMoneyCommand Handler");

        Money currentBalance = this.memberMoney.getBalance();
        Money decreaseAmount = new Money(command.amount());
        if (currentBalance.value().compareTo(decreaseAmount.value()) < 0) {
            throw new DomainRuleException(String.format("잔액 부족: 현재 잔액 %s, 차감 요청 금액 %s",
                    currentBalance.value(), decreaseAmount.value()));
        }

        AggregateLifecycle.apply(new MemberMoneyDecreasedEvent(
                command.memberMoneyId(),
                command.amount()
        ));
    }

    @EventSourcingHandler
    public void on(MemberMoneyDecreasedEvent event) {
        log.info("MemberMoneyDecreasedEvent Handler");
        this.memberMoney.decreaseBalance(new Money(event.amount()));
    }
}
