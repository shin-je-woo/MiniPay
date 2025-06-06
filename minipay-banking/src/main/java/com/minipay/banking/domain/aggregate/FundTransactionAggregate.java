package com.minipay.banking.domain.aggregate;

import com.minipay.banking.adapter.in.axon.commnad.CreateWithdrawalFundCommand;
import com.minipay.banking.adapter.in.axon.commnad.FailWithdrawalFundCommand;
import com.minipay.banking.adapter.in.axon.commnad.SucceedWithdrawalFundCommand;
import com.minipay.banking.domain.event.WithdrawalFundCreatedEvent;
import com.minipay.banking.domain.event.WithdrawalFundFailedEvent;
import com.minipay.banking.domain.event.WithdrawalFundSucceededEvent;
import com.minipay.banking.domain.model.*;
import com.minipay.saga.command.FailDepositFundCommand;
import com.minipay.saga.command.OrderDepositFundCommand;
import com.minipay.saga.command.SucceedDepositFundCommand;
import com.minipay.saga.event.DepositFundCreatedEvent;
import com.minipay.saga.event.DepositFundFailedEvent;
import com.minipay.saga.event.DepositFundSucceededEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Aggregate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundTransactionAggregate {

    @AggregateIdentifier
    private UUID fundTransactionId;

    private FundTransaction fundTransaction;

    @CommandHandler
    public FundTransactionAggregate(OrderDepositFundCommand command) {
        log.info("OrderDepositFundCommand Handler");

        FundTransaction createdFund = FundTransaction.depositInstance(
                new BankAccount.BankAccountId(command.bankAccountId()),
                new Money(command.amount())
        );

        AggregateLifecycle.apply(new DepositFundCreatedEvent(
                command.orderDepositFundId(),
                createdFund.getFundTransactionId().value(),
                command.bankAccountId(),
                command.checkBankAccountId(),
                command.memberMoneyId(),
                command.moneyHistoryId(),
                command.amount(),
                command.bankName(),
                command.accountNumber(),
                createdFund.getFundType().name(),
                createdFund.getStatus().name(),
                createdFund.getMinipayBankAccount().name()
        ));
    }

    @EventSourcingHandler
    public void on(DepositFundCreatedEvent event) {
        log.info("DepositFundCreatedEvent Sourcing Handler");
        this.fundTransactionId = event.fundTransactionId();
        this.fundTransaction = FundTransaction.withId(
                new FundTransaction.FundTransactionId(event.fundTransactionId()),
                new BankAccount.BankAccountId(event.bankAccountId()),
                MinipayBankAccount.valueOf(event.minipayBankAccount()),
                null,
                FundTransaction.FundType.valueOf(event.fundType()),
                new Money(event.amount()),
                FundTransaction.FundTransactionStatus.valueOf(event.fundTransactionStatus())
        );
    }

    @CommandHandler
    public void handle(SucceedDepositFundCommand command) {
        log.info("SucceedDepositFundCommand Handler");
        AggregateLifecycle.apply(new DepositFundSucceededEvent(
                command.fundTransactionId(),
                command.orderDepositFundId(),
                command.bankAccountId(),
                command.checkBankAccountId(),
                command.memberMoneyId(),
                command.moneyHistoryId(),
                command.amount(),
                command.bankName(),
                command.accountNumber()
        ));
    }

    @EventSourcingHandler
    public void on(DepositFundSucceededEvent event) {
        log.info("DepositFundSucceededEvent Sourcing Handler");
        this.fundTransaction.success();
    }

    @CommandHandler
    public void handle(FailDepositFundCommand command) {
        log.info("FailDepositFundCommand Handler");
        AggregateLifecycle.apply(new DepositFundFailedEvent(
                command.fundTransactionId(),
                command.orderDepositFundId(),
                command.bankAccountId(),
                command.checkBankAccountId(),
                command.memberMoneyId(),
                command.moneyHistoryId(),
                command.amount(),
                command.bankName(),
                command.accountNumber()
        ));
    }

    @EventSourcingHandler
    public void on(DepositFundFailedEvent event) {
        log.info("DepositFundFailedEvent Sourcing Handler");
        this.fundTransaction.fail();
    }

    @CommandHandler
    public FundTransactionAggregate(CreateWithdrawalFundCommand command) {
        log.info("CreateWithdrawalFundCommand Handler");

        FundTransaction withdrawalTransaction = FundTransaction.withdrawalInstance(
                new BankAccount.BankAccountId(command.bankAccountId()),
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(command.bankName()),
                        new ExternalBankAccount.AccountNumber(command.bankAccountNumber())
                ),
                new Money(command.amount())
        );
        Objects.requireNonNull(withdrawalTransaction.getWithdrawalBankAccount());

        AggregateLifecycle.apply(new WithdrawalFundCreatedEvent(
                withdrawalTransaction.getFundTransactionId().value(),
                withdrawalTransaction.getBankAccountId().value(),
                withdrawalTransaction.getWithdrawalBankAccount().bankName().value(),
                withdrawalTransaction.getWithdrawalBankAccount().accountNumber().value(),
                withdrawalTransaction.getMinipayBankAccount().name(),
                withdrawalTransaction.getMinipayBankAccount().getBankName(),
                withdrawalTransaction.getMinipayBankAccount().getAccountNumber(),
                withdrawalTransaction.getFundType().name(),
                withdrawalTransaction.getAmount().value(),
                withdrawalTransaction.getStatus().name()
        ));
    }

    @EventSourcingHandler
    public void on(WithdrawalFundCreatedEvent event) {
        log.info("WithdrawalFundCreatedEvent Sourcing Handler");
        this.fundTransactionId = event.fundTransactionId();
        this.fundTransaction = FundTransaction.withId(
                new FundTransaction.FundTransactionId(event.fundTransactionId()),
                new BankAccount.BankAccountId(event.bankAccountId()),
                MinipayBankAccount.valueOf(event.minipayBankAccount()),
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(event.withdrawalBankName()),
                        new ExternalBankAccount.AccountNumber(event.withdrawalAccountNumber())
                ),
                FundTransaction.FundType.valueOf(event.fundType()),
                new Money(event.amount()),
                FundTransaction.FundTransactionStatus.valueOf(event.status())
        );
    }

    @CommandHandler
    public void handle(SucceedWithdrawalFundCommand command) {
        log.info("SucceedWithdrawalFundCommand Handler");
        AggregateLifecycle.apply(new WithdrawalFundSucceededEvent(command.fundTransactionId()));
    }

    @EventSourcingHandler
    public void on(WithdrawalFundSucceededEvent event) {
        log.info("WithdrawalFundSucceededEvent Sourcing Handler");
        this.fundTransaction.success();
    }

    @CommandHandler
    public void handle(FailWithdrawalFundCommand command) {
        log.info("FailWithdrawalFundCommand Handler");
        AggregateLifecycle.apply(new WithdrawalFundFailedEvent(command.fundTransactionId()));
    }

    @EventSourcingHandler
    public void on(WithdrawalFundFailedEvent event) {
        log.info("WithdrawalFundFailedEvent Sourcing Handler");
        this.fundTransaction.fail();
    }
}
