package com.minipay.banking.application.service;

import com.minipay.banking.adapter.in.axon.commnad.CreateDepositFundCommand;
import com.minipay.banking.application.event.DepositFundEventFactory;
import com.minipay.banking.application.port.in.DepositFundByAxonCommand;
import com.minipay.banking.application.port.in.DepositFundCommand;
import com.minipay.banking.application.port.in.DepositFundUseCase;
import com.minipay.banking.application.port.out.*;
import com.minipay.banking.domain.event.DepositFundCreatedEvent;
import com.minipay.banking.domain.event.FundTransactionEvent;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.FundTransaction;
import com.minipay.banking.domain.model.MinipayBankAccount;
import com.minipay.banking.domain.model.Money;
import com.minipay.common.annotation.UseCase;
import com.minipay.common.event.EventType;
import com.minipay.common.event.Events;
import com.minipay.common.exception.BusinessException;
import com.minipay.saga.command.OrderDepositFundCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class DepositFundService implements DepositFundUseCase {

    private final BankAccountPersistencePort bankAccountPersistencePort;
    private final ExternalBankingPort externalBankingPort;
    private final FundTransactionPersistencePort fundTransactionPersistencePort;
    private final CommandGateway commandGateway;
    private final EventGateway eventGateway;
    private final DepositFundEventFactory eventFactory;

    @Override
    public void deposit(DepositFundCommand command) {
        // 1. 요청받은 계좌 확인
        BankAccount bankAccount = bankAccountPersistencePort.readBankAccount(new BankAccount.BankAccountId(command.getBankAccountId()));

        // 2. 외부 은행에서 계좌 정보 가져오기
        ExternalBankAccountInfo externalBankAccountInfo = externalBankingPort.getBankAccountInfo(
                bankAccount.getLinkedBankAccount().bankName().value(),
                bankAccount.getLinkedBankAccount().accountNumber().value()
        );
        if (!externalBankAccountInfo.isValid()) {
            throw new BusinessException("연결된 계좌의 상태가 유효하지 않습니다");
        }

        // 3. 외부 은행에 펌뱅킹 요청
        FirmBankingRequest firmBankingRequest = FirmBankingRequest.builder()
                .srcBankName(externalBankAccountInfo.bankName())
                .srcAccountNumber(externalBankAccountInfo.accountNumber())
                .destBankName(MinipayBankAccount.NORMAL_ACCOUNT.getBankName())
                .destAccountNumber(MinipayBankAccount.NORMAL_ACCOUNT.getAccountNumber())
                .amount(command.getAmount())
                .build();
        FirmBankingResult firmBankingResult = externalBankingPort.requestFirmBanking(firmBankingRequest);
        if (!firmBankingResult.isSucceeded()) {
            throw new BusinessException("펌뱅킹 요청이 실패했습니다.");
        }

        FundTransaction fundTransaction = FundTransaction.depositInstance(
                new BankAccount.BankAccountId(command.getBankAccountId()),
                new Money(command.getAmount())
        );
        fundTransactionPersistencePort.createFundTransaction(fundTransaction);
        Events.raise(FundTransactionEvent.of(EventType.MINIPAY_FUND_DEPOSITED, fundTransaction, command.getMoneyHistoryId()));
    }

    @Override
    public void depositByAxon(DepositFundByAxonCommand command) {
        CreateDepositFundCommand createDepositFundCommand = new CreateDepositFundCommand(command.getBankAccountId(), command.getAmount());
        commandGateway.send(createDepositFundCommand);
    }

    @Override
    public FirmBankingResult processDepositByAxon(DepositFundCreatedEvent event) {
        BankAccount bankAccount = bankAccountPersistencePort.readBankAccount(new BankAccount.BankAccountId(event.bankAccountId()));
        // 외부 은행에 펌뱅킹 요청
        FirmBankingRequest firmBankingRequest = FirmBankingRequest.builder()
                .srcBankName(bankAccount.getLinkedBankAccount().bankName().value())
                .srcAccountNumber(bankAccount.getLinkedBankAccount().accountNumber().value())
                .destBankName(MinipayBankAccount.NORMAL_ACCOUNT.getBankName())
                .destAccountNumber(MinipayBankAccount.NORMAL_ACCOUNT.getAccountNumber())
                .amount(event.amount())
                .build();
        return externalBankingPort.requestFirmBanking(firmBankingRequest);
    }

    @Override
    public void depositByAxonSaga(OrderDepositFundCommand command) {
        // 외부 은행에 펌뱅킹 요청
        FirmBankingRequest firmBankingRequest = FirmBankingRequest.builder()
                .srcBankName(command.bankName())
                .srcAccountNumber(command.accountNumber())
                .destBankName(MinipayBankAccount.NORMAL_ACCOUNT.getBankName())
                .destAccountNumber(MinipayBankAccount.NORMAL_ACCOUNT.getAccountNumber())
                .amount(command.amount())
                .build();
        FirmBankingResult firmBankingResult = externalBankingPort.requestFirmBanking(firmBankingRequest);

        eventGateway.publish(firmBankingResult.isSucceeded()
                ? eventFactory.successEvent(command)
                : eventFactory.failureEvent(command));
    }
}
