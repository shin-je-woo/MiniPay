package com.minipay.banking.adapter.out.bank;

import com.minipay.banking.application.port.out.*;
import com.minipay.common.annotation.ExternalSystemAdapter;

import java.util.concurrent.ThreadLocalRandom;

@ExternalSystemAdapter
public class ExternalBankAccountApiAdapter implements ExternalBankingPort {

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

        boolean isSucceeded = switch (randomResponseStatus) {
            case 1 -> true;
            case 2 -> false;
            default -> throw new IllegalStateException("펌뱅킹 요청 결과값이 올바르지 않습니다.");
        };

        return new FirmBankingResult(isSucceeded);
    }
}
