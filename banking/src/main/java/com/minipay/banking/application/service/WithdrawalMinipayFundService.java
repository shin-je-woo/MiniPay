package com.minipay.banking.application.service;

import com.minipay.banking.application.port.in.WithdrawalMinipayFundUseCase;
import com.minipay.banking.application.port.in.WithdrawalMinipayMoneyCommand;
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
public class WithdrawalMinipayFundService implements WithdrawalMinipayFundUseCase {

    private final BankAccountPersistencePort bankAccountPersistencePort;
    private final MinipayFundPersistencePort minipayFundPersistencePort;
    private final ExternalBankingPort externalBankingPort;

    @Override
    public void withdrawal(WithdrawalMinipayMoneyCommand command) {
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
                .srcBankName(MinipayBankAccount.NORMAL_ACCOUNT.getBankName())
                .srcAccountNumber(MinipayBankAccount.NORMAL_ACCOUNT.getAccountNumber())
                .destBankName(externalBankAccountInfo.bankName())
                .destAccountNumber(externalBankAccountInfo.accountNumber())
                .amount(command.getAmount())
                .build();
        FirmBankingResult firmBankingResult = externalBankingPort.requestFirmBanking(firmBankingRequest);
        if (!firmBankingResult.isSucceeded()) {
            throw new BusinessException("펌뱅킹 요청이 실패했습니다.");
        }

        MinipayFund minipayFund = MinipayFund.withdrawalInstance(
                new BankAccount.BankAccountId(command.getBankAccountId()),
                new Money(command.getAmount())
        );
        minipayFundPersistencePort.storeMinipayFund(minipayFund);
        Events.raise(MinipayFundEvent.of(EventType.MINIPAY_FUND_WITHDRAWN, minipayFund));
    }
}
