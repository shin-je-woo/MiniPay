package com.minipay.banking.application.service;

import com.minipay.banking.application.port.in.RequestTransferMoneyUseCase;
import com.minipay.banking.application.port.in.TransferMoneyCommand;
import com.minipay.banking.application.port.out.*;
import com.minipay.banking.domain.TransferMoney;
import com.minipay.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RequestTransferMoneyService implements RequestTransferMoneyUseCase {

    private final CreateTransferMoneyPort createTransferMoneyPort;
    private final RequestFirmBankingPort requestFirmBankingPort;
    private final ModifyTransferMoneyPort modifyTransferMoneyPort;

    @Override
    public TransferMoney requestTransferMoney(TransferMoneyCommand command) {
        // TODO from 계좌가 요청한 사용자의 계좌인지 확인 -> IPC
        // TODO 송금을 위한 Lock?
        // TODO request 트랜잭션이랑 성공/실패 트랜잭션 분리 필요?
        // 1. 송금 요청을 저장 (요청 상태로)
        TransferMoney transferMoney = TransferMoney.create(
                new TransferMoney.FirmBankingAccount(command.getFromBankName(), command.getFromBankAccountNumber()),
                new TransferMoney.FirmBankingAccount(command.getToBankName(), command.getToBankAccountNumber()),
                new TransferMoney.MoneyAmount(command.getMoneyAmount())
        );
        TransferMoney savedTransferMoney = createTransferMoneyPort.createTransferMoney(transferMoney);

        // 2. 외부 은행에 펌뱅킹 요청
        FirmBankingRequest firmBankingRequest = FirmBankingRequest.builder()
                .fromBankName(command.getFromBankName())
                .fromBankAccountNumber(command.getFromBankAccountNumber())
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .amount(command.getMoneyAmount())
                .build();
        FirmBankingResult firmBankingResult = requestFirmBankingPort.requestFirmBanking(firmBankingRequest);

        // 3. 결과에 따라 1번에 저장한 송금 요청 update
        TransferMoney modifedTransferMoney = savedTransferMoney.changeStatus(firmBankingResult.transferMoneyStatus());
        return modifyTransferMoneyPort.modifyTransferMoney(modifedTransferMoney);
    }
}
