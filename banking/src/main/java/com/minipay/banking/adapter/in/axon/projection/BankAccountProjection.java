package com.minipay.banking.adapter.in.axon.projection;

import com.minipay.banking.application.port.out.BankAccountPersistencePort;
import com.minipay.banking.domain.event.BankAccountCreatedEvent;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.ExternalBankAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("bankAccount-projection")
public class BankAccountProjection {

    private final BankAccountPersistencePort bankAccountPersistencePort;

    @EventHandler
    public void on(BankAccountCreatedEvent event) {
        log.info("BankAccountCreatedEvent Handler");
        BankAccount bankAccount = BankAccount.withId(
                new BankAccount.BankAccountId(event.bankAccountId()),
                new BankAccount.MembershipId(event.membershipId()),
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(event.bankName()),
                        new ExternalBankAccount.AccountNumber(event.accountNumber())
                ),
                BankAccount.LinkedStatus.valueOf(event.linkedStatus())
        );
        bankAccountPersistencePort.createBankAccount(bankAccount);
    }
}
