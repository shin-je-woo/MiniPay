package com.minipay.banking.adapter.in.axon.projection;

import com.minipay.banking.application.port.out.FundTransactionPersistencePort;
import com.minipay.banking.domain.event.DepositFundCreatedEvent;
import com.minipay.banking.domain.event.WithdrawalFundCreatedEvent;
import com.minipay.banking.domain.model.*;
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

    // 입금 요청 이벤트 핸들러
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

    // 출금 요청 이벤트 핸들러
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
}
