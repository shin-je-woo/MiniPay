package com.minipay.banking.adapter.in.axon.projection;

import com.minipay.banking.application.port.out.FundTransactionPersistencePort;
import com.minipay.banking.domain.event.DepositFundCreatedEvent;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.FundTransaction;
import com.minipay.banking.domain.model.MinipayBankAccount;
import com.minipay.banking.domain.model.Money;
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
                FundTransaction.FundType.valueOf(event.fundType()),
                FundTransaction.FundTransactionStatus.valueOf(event.status()),
                new Money(event.amount())
        );
        fundTransactionPersistencePort.createFundTransaction(fundTransaction);
    }
}
