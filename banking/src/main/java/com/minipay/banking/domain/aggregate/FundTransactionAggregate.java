package com.minipay.banking.domain.aggregate;

import com.minipay.banking.adapter.in.axon.commnad.CreateDepositFundCommand;
import com.minipay.banking.adapter.in.axon.commnad.CreateWithdrawalFundCommand;
import com.minipay.banking.domain.event.DepositFundCreatedEvent;
import com.minipay.banking.domain.event.WithdrawalFundCreatedEvent;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.FundTransaction;
import com.minipay.banking.domain.model.MinipayBankAccount;
import com.minipay.banking.domain.model.Money;
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
                FundTransaction.FundType.valueOf(event.fundType()),
                FundTransaction.FundTransactionStatus.valueOf(event.status()),
                new Money(event.amount())
        );
    }

    // 출금 커맨드 핸들러
    @CommandHandler
    public FundTransactionAggregate(CreateWithdrawalFundCommand command) {
        log.info("CreateWithdrawalFundCommand Handler");

        FundTransaction createdFund = FundTransaction.withdrawalInstance(
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

    // 출금 이벤트 핸들러
    @EventSourcingHandler
    public void on(WithdrawalFundCreatedEvent event) {
        log.info("WithdrawalFundCreatedEvent Sourcing Handler");
        this.fundTransactionId = event.fundTransactionId();
        this.fundTransaction = FundTransaction.withId(
                new FundTransaction.FundTransactionId(event.fundTransactionId()),
                new BankAccount.BankAccountId(event.bankAccountId()),
                MinipayBankAccount.valueOf(event.minipayBankAccount()),
                FundTransaction.FundType.valueOf(event.fundType()),
                FundTransaction.FundTransactionStatus.valueOf(event.status()),
                new Money(event.amount())
        );
    }
}
