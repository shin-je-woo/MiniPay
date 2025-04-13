package com.minipay.banking.domain.aggregate;

import com.minipay.banking.adapter.in.axon.commnad.CreateBankAccountCommand;
import com.minipay.banking.application.port.in.BankAccountValidateUseCase;
import com.minipay.banking.domain.event.BankAccountCreatedEvent;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.ExternalBankAccount;
import com.minipay.saga.command.CheckBankAccountCommand;
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
public class BankAccountAggregate {

    @AggregateIdentifier
    private UUID bankAccountId;

    private BankAccount bankAccount;

    @CommandHandler
    public BankAccountAggregate(CreateBankAccountCommand command) {
        log.info("CreateBankAccountCommand Handler");

        BankAccount createdBankAccount = BankAccount.newInstance(
                new BankAccount.MembershipId(command.membershipId()),
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(command.bankName()),
                        new ExternalBankAccount.AccountNumber(command.accountNumber())
                )
        );

        AggregateLifecycle.apply(new BankAccountCreatedEvent(
                createdBankAccount.getBankAccountId().value(),
                createdBankAccount.getMembershipId().value(),
                createdBankAccount.getLinkedBankAccount().bankName().value(),
                createdBankAccount.getLinkedBankAccount().accountNumber().value(),
                createdBankAccount.getLinkedStatus().name()
        ));
    }

    @EventSourcingHandler
    public void on(BankAccountCreatedEvent event) {
        log.info("BankAccountCreatedEvent Sourcing Handler");
        this.bankAccountId = event.bankAccountId();
        this.bankAccount = BankAccount.withId(
                new BankAccount.BankAccountId(event.bankAccountId()),
                new BankAccount.MembershipId(event.membershipId()),
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(event.bankName()),
                        new ExternalBankAccount.AccountNumber(event.accountNumber())
                ),
                BankAccount.LinkedStatus.valueOf(event.linkedStatus())
        );
    }

    @CommandHandler
    public void handle(CheckBankAccountCommand command, BankAccountValidateUseCase bankAccountValidateUseCase) {
        log.info("CheckLinkedBankAccountCommand Handler");

        // command 를 통해, 이 어그리거트(BankAccount)가 정상인지를 확인
        bankAccountValidateUseCase.validateLinkedAccount(command);
    }
}
