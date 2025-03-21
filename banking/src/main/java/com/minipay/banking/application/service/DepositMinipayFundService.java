package com.minipay.banking.application.service;

import com.minipay.banking.application.port.in.DepositMinipayMoneyCommand;
import com.minipay.banking.application.port.in.DepositMinipayFundUseCase;
import com.minipay.banking.application.port.out.*;
import com.minipay.banking.domain.*;
import com.minipay.common.annotation.UseCase;
import com.minipay.common.event.EventType;
import com.minipay.common.event.Events;
import com.minipay.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class DepositMinipayFundService implements DepositMinipayFundUseCase {

    private final BankAccountPersistencePort bankAccountPersistencePort;
    private final ExternalBankingPort externalBankingPort;
    private final MinipayFundPersistencePort minipayFundPersistencePort;

    @Override
    public void deposit(DepositMinipayMoneyCommand command) {
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

        MinipayFund minipayFund = MinipayFund.depositInstance(
                new BankAccount.BankAccountId(command.getBankAccountId()),
                new Money(command.getAmount())
        );
        minipayFundPersistencePort.storeMinipayFund(minipayFund);
        Events.raise(MinipayFundEvent.of(EventType.MINIPAY_FUND_DEPOSITED, minipayFund, command.getMoneyHistoryId()));
    }
}
