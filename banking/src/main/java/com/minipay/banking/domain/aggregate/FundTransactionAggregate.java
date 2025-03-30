package com.minipay.banking.domain.aggregate;

import com.minipay.banking.adapter.in.axon.commnad.CreateDepositFundCommand;
import com.minipay.banking.adapter.in.axon.commnad.CreateWithdrawalFundCommand;
import com.minipay.banking.domain.event.DepositFundCreatedEvent;
import com.minipay.banking.domain.event.WithdrawalFundCreatedEvent;
import com.minipay.banking.domain.model.*;
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

    // 입금 커맨드 핸들러
    @CommandHandler
    public FundTransactionAggregate(CreateDepositFundCommand command) {
        log.info("CreateDepositFundCommand Handler");

        FundTransaction createdFund = FundTransaction.depositInstance(
                new BankAccount.BankAccountId(command.bankAccountId()),
                new Money(command.amount())
        );

        AggregateLifecycle.apply(new DepositFundCreatedEvent(
                createdFund.getFundTransactionId().value(),
                createdFund.getBankAccountId().value(),
                createdFund.getFundType().name(),
                createdFund.getStatus().name(),
                createdFund.getAmount().value(),
                createdFund.getMinipayBankAccount().name(),
                createdFund.getMinipayBankAccount().getBankName(),
                createdFund.getMinipayBankAccount().getAccountNumber()
        ));
    }

    // 입금 이벤트 핸들러
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
                FundTransaction.FundTransactionStatus.valueOf(event.status())
        );
    }

    // 출금 커맨드 핸들러
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

    // 출금 이벤트 핸들러
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
}
