package com.minipay.banking.application.service;

import com.minipay.banking.adapter.in.axon.commnad.CreateWithdrawalFundCommand;
import com.minipay.banking.application.port.in.WithdrawalFundByAxonCommand;
import com.minipay.banking.application.port.in.WithdrawalFundCommand;
import com.minipay.banking.application.port.in.WithdrawalFundUseCase;
import com.minipay.banking.application.port.out.*;
import com.minipay.banking.domain.event.FundTransactionEvent;
import com.minipay.banking.domain.event.WithdrawalFundCreatedEvent;
import com.minipay.banking.domain.model.*;
import com.minipay.common.annotation.UseCase;
import com.minipay.common.event.EventType;
import com.minipay.common.event.Events;
import com.minipay.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class WithdrawalFundService implements WithdrawalFundUseCase {

    private final BankAccountPersistencePort bankAccountPersistencePort;
    private final FundTransactionPersistencePort fundTransactionPersistencePort;
    private final ExternalBankingPort externalBankingPort;
    private final CommandGateway commandGateway;

    @Override
    public void withdrawal(WithdrawalFundCommand command) {
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
                .srcBankName(MinipayBankAccount.CORPORATE_ACCOUNT.getBankName())
                .srcAccountNumber(MinipayBankAccount.CORPORATE_ACCOUNT.getAccountNumber())
                .destBankName(command.getBankName())
                .destAccountNumber(command.getBankAccountNumber())
                .amount(command.getAmount())
                .build();
        FirmBankingResult firmBankingResult = externalBankingPort.requestFirmBanking(firmBankingRequest);
        if (!firmBankingResult.isSucceeded()) {
            throw new BusinessException("펌뱅킹 요청이 실패했습니다.");
        }

        FundTransaction fundTransaction = FundTransaction.withdrawalInstance(
                new BankAccount.BankAccountId(command.getBankAccountId()),
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(command.getBankName()),
                        new ExternalBankAccount.AccountNumber(command.getBankAccountNumber())
                ),
                new Money(command.getAmount())
        );
        fundTransaction.success();
        fundTransactionPersistencePort.createFundTransaction(fundTransaction);
        Events.raise(FundTransactionEvent.of(EventType.MINIPAY_FUND_WITHDRAWN, fundTransaction));
    }

    @Override
    public void withdrawalByAxon(WithdrawalFundByAxonCommand command) {
        CreateWithdrawalFundCommand createWithdrawalFundCommand = new CreateWithdrawalFundCommand(
                command.getBankAccountId(),
                command.getBankName(),
                command.getBankAccountNumber(),
                command.getAmount()
        );
        commandGateway.send(createWithdrawalFundCommand);
    }

    @Override
    public FirmBankingResult processWithdrawalByAxon(WithdrawalFundCreatedEvent event) {
        // 외부 은행에 펌뱅킹 요청
        FirmBankingRequest firmBankingRequest = FirmBankingRequest.builder()
                .srcBankName(MinipayBankAccount.CORPORATE_ACCOUNT.getBankName())
                .srcAccountNumber(MinipayBankAccount.CORPORATE_ACCOUNT.getAccountNumber())
                .destBankName(event.withdrawalBankName())
                .destAccountNumber(event.withdrawalAccountNumber())
                .amount(event.amount())
                .build();
        return externalBankingPort.requestFirmBanking(firmBankingRequest);
    }
}
