package com.minipay.banking.application.service;

import com.minipay.banking.application.port.in.RequestTransferMoneyUseCase;
import com.minipay.banking.application.port.in.TransferMoneyCommand;
import com.minipay.banking.application.port.out.*;
import com.minipay.banking.domain.ExternalBankAccount;
import com.minipay.banking.domain.Money;
import com.minipay.banking.domain.TransferMoney;
import com.minipay.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RequestTransferMoneyService implements RequestTransferMoneyUseCase {

    private final TransferMoneyPersistencePort transferMoneyPersistencePort;
    private final ExternalBankingPort externalBankingPort;

    @Override
    public TransferMoney requestTransferMoney(TransferMoneyCommand command) {
        // TODO from 계좌가 요청한 사용자의 계좌인지 확인 -> IPC
        // TODO 송금을 위한 Lock?
        // TODO request 트랜잭션이랑 성공/실패 트랜잭션 분리 필요?
        // 1. 송금 요청을 저장 (요청 상태로)
        TransferMoney transferMoney = TransferMoney.newInstance(
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(command.getSrcBankName()),
                        new ExternalBankAccount.AccountNumber(command.getSrcAccountNumber())
                ),
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(command.getDestBankName()),
                        new ExternalBankAccount.AccountNumber(command.getDestAccountNumber())
                ),
                new Money(command.getAmount())
        );
        TransferMoney savedTransferMoney = transferMoneyPersistencePort.createTransferMoney(transferMoney);

        // 2. 외부 은행에 펌뱅킹 요청
        FirmBankingRequest firmBankingRequest = FirmBankingRequest.builder()
                .srcBankName(command.getSrcBankName())
                .srcAccountNumber(command.getSrcAccountNumber())
                .destBankName(command.getDestBankName())
                .destAccountNumber(command.getDestAccountNumber())
                .amount(command.getAmount())
                .build();
        FirmBankingResult firmBankingResult = externalBankingPort.requestFirmBanking(firmBankingRequest);

        // 3. 결과에 따라 1번에 저장한 송금 요청 update
        TransferMoney modifiedTransferMoney = savedTransferMoney.changeStatus(
                firmBankingResult.isSucceeded() ? TransferMoney.TransferMoneyStatus.SUCCEEDED : TransferMoney.TransferMoneyStatus.FAILED
        );

        return transferMoneyPersistencePort.updateTransferMoney(modifiedTransferMoney);
    }
}
