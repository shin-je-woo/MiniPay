package com.minipay.banking.adapter.in.axon.projection;

import com.minipay.banking.application.port.out.FundTransactionPersistencePort;
import com.minipay.banking.domain.event.*;
import com.minipay.banking.domain.model.*;
import com.minipay.saga.event.OrderDepositFundSucceededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("fundTransaction-projection")
public class FundTransactionProjection {

    private final FundTransactionPersistencePort fundTransactionPersistencePort;

    @EventHandler
    public void on(DepositFundCreatedEvent event) {
        log.info("DepositFundCreatedEvent Handler");
        FundTransaction fundTransaction = FundTransaction.withId(
                new FundTransaction.FundTransactionId(event.fundTransactionId()),
                new BankAccount.BankAccountId(event.bankAccountId()),
                MinipayBankAccount.valueOf(event.minipayBankAccount()),
                null,
                FundTransaction.FundType.valueOf(event.fundType()),
                new Money(event.amount()),
                FundTransaction.FundTransactionStatus.valueOf(event.status())
        );
        fundTransactionPersistencePort.createFundTransaction(fundTransaction);
    }

    @EventHandler
    public void on(OrderDepositFundSucceededEvent event) {
        log.info("OrderDepositFundSucceededEvent Handler");
        FundTransaction fundTransaction = FundTransaction.depositInstance(
                new BankAccount.BankAccountId(event.bankAccountId()),
                new Money(event.amount())
        );
        fundTransactionPersistencePort.createFundTransaction(fundTransaction);
    }

    @EventHandler
    public void on(WithdrawalFundCreatedEvent event) {
        log.info("WithdrawalFundCreatedEvent Handler");
        FundTransaction fundTransaction = FundTransaction.withId(
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
        fundTransactionPersistencePort.createFundTransaction(fundTransaction);
    }

    @EventHandler
    public void on(DepositFundSucceededEvent event) {
        log.info("DepositFundSucceededEvent Handler");
        FundTransaction fundTransaction = fundTransactionPersistencePort.readFundTransaction(new FundTransaction.FundTransactionId(event.fundTransactionId()));
        fundTransaction.success();
        fundTransactionPersistencePort.updateFundTransaction(fundTransaction);
    }

    @EventHandler
    public void on(DepositFundFailedEvent event) {
        log.info("DepositFundFailedEvent Handler");
        FundTransaction fundTransaction = fundTransactionPersistencePort.readFundTransaction(new FundTransaction.FundTransactionId(event.fundTransactionId()));
        fundTransaction.fail();
        fundTransactionPersistencePort.updateFundTransaction(fundTransaction);
    }

    @EventHandler
    public void on(WithdrawalFundSucceededEvent event) {
        log.info("WithdrawalFundSucceededEvent Handler");
        FundTransaction fundTransaction = fundTransactionPersistencePort.readFundTransaction(new FundTransaction.FundTransactionId(event.fundTransactionId()));
        fundTransaction.success();
        fundTransactionPersistencePort.updateFundTransaction(fundTransaction);
    }

    @EventHandler
    public void on(WithdrawalFundFailedEvent event) {
        log.info("WithdrawalFundFailedEvent Handler");
        FundTransaction fundTransaction = fundTransactionPersistencePort.readFundTransaction(new FundTransaction.FundTransactionId(event.fundTransactionId()));
        fundTransaction.fail();
        fundTransactionPersistencePort.updateFundTransaction(fundTransaction);
    }
}
