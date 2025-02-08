package com.minipay.banking.adapter.out.bank;

import com.minipay.banking.application.port.out.*;
import com.minipay.banking.domain.TransferMoney;
import com.minipay.common.annotation.ExternalSystemAdapter;

import java.util.concurrent.ThreadLocalRandom;

@ExternalSystemAdapter
public class BankAccountApiAdapter implements GetBankAccountInfoPort, RequestFirmBankingPort {

    @Override
    public ExternalBankAccountInfo getBankAccountInfo(String bankName, String accountNumber) {
        // TODO 실제 은행계좌를 HTTP 를 통해 가져오기. 지금은 유효한 계좌정보 리턴하도록 Mocking
        return new ExternalBankAccountInfo(bankName, accountNumber, true);
    }

    @Override
    public FirmBankingResult requestFirmBanking(FirmBankingRequest firmBankingRequest) {
        // TODO 실제 은행에 펌뱅킹 요청. 지금은 성공한 펌뱅킹 결과 리턴하도록 Mocking
        // 은행 HTTP 요청 결과에 따라 도메인 영역 코드로 맵핑해서 반환
        int randomResponseStatus = ThreadLocalRandom.current().nextInt(3);

        TransferMoney.TransferMoneyStatus transferMoneyStatus =
                switch (randomResponseStatus) {
                    case 1 -> TransferMoney.TransferMoneyStatus.SUCCESS;
                    case 2 -> TransferMoney.TransferMoneyStatus.FAILED;
                    default -> throw new IllegalStateException("펌뱅킹 요청 결과값이 올바르지 않습니다.");
                };

        return new FirmBankingResult(transferMoneyStatus);
    }
}
