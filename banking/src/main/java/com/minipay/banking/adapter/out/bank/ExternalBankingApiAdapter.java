package com.minipay.banking.adapter.out.bank;

import com.minipay.banking.application.port.out.*;
import com.minipay.common.annotation.ExternalSystemAdapter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@ExternalSystemAdapter
public class ExternalBankingApiAdapter implements ExternalBankingPort {

    @Override
    public ExternalBankAccountInfo getBankAccountInfo(String bankName, String accountNumber) {
        // TODO 실제 은행계좌를 HTTP 를 통해 가져오기. 지금은 유효한 계좌정보 리턴하도록 Mocking
        return new ExternalBankAccountInfo(bankName, accountNumber, true);
    }

    @Override
    @SneakyThrows(InterruptedException.class)
    public FirmBankingResult requestFirmBanking(FirmBankingRequest firmBankingRequest) {
        // TODO 실제 은행에 펌뱅킹 요청. 지금은 성공한 펌뱅킹 결과 리턴하도록 Mocking
        // 은행 HTTP 요청 결과에 따라 도메인 영역 코드로 맵핑해서 반환
        int randomResponseStatus = ThreadLocalRandom.current().nextInt(3);
        Thread.sleep(3000);

        boolean isSucceeded = switch (randomResponseStatus) {
            case 1 -> true;
            case 2 -> false;
            default -> {
                log.warn("펌뱅킹 요청 결과가 성공하지 않았습니다. 펌뱅킹 요청 결과: {}", randomResponseStatus);
                yield  false;
            }
        };

        return new FirmBankingResult(isSucceeded);
    }
}
