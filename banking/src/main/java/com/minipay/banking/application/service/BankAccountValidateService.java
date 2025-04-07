package com.minipay.banking.application.service;

import com.minipay.banking.application.event.BankAccountValidationEventFactory;
import com.minipay.banking.application.port.in.BankAccountValidateUseCase;
import com.minipay.banking.application.port.out.BankAccountPersistencePort;
import com.minipay.banking.application.port.out.ExternalBankAccountInfo;
import com.minipay.banking.application.port.out.ExternalBankingPort;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.common.annotation.UseCase;
import com.minipay.saga.command.CheckLinkedBankAccountCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class BankAccountValidateService implements BankAccountValidateUseCase {

    private final BankAccountPersistencePort bankAccountPersistencePort;
    private final ExternalBankingPort externalBankingPort;
    private final BankAccountValidationEventFactory eventFactory;
    private final EventGateway eventGateway;

    @Override
    public void validateLinkedAccount(CheckLinkedBankAccountCommand command) {
        BankAccount bankAccount = bankAccountPersistencePort.readBankAccount(
                new BankAccount.BankAccountId(command.bankAccountId())
        );

        String bankName = bankAccount.getLinkedBankAccount().bankName().value();
        String accountNumber = bankAccount.getLinkedBankAccount().accountNumber().value();

        ExternalBankAccountInfo externalInfo = getAccountInfoSafely(command, bankName, accountNumber);

//        eventGateway.publish(externalInfo.isValid()
//                ? eventFactory.successEvent(command)
//                : eventFactory.failureEvent(command));
        eventGateway.publish(eventFactory.failureEvent(command));
    }

    private ExternalBankAccountInfo getAccountInfoSafely(CheckLinkedBankAccountCommand command, String bankName, String accountNumber) {
        try {
            return externalBankingPort.getBankAccountInfo(bankName, accountNumber);
        } catch (Exception e) {
            log.error("외부 뱅킹 서비스 오류 - BankAccountId: {}, 이유: {}", command.bankAccountId(), e.getMessage());
            return new ExternalBankAccountInfo(bankName, accountNumber, false);
        }
    }
}
