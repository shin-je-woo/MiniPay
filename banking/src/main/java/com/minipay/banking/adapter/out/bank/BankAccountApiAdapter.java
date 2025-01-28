package com.minipay.banking.adapter.out.bank;

import com.minipay.banking.application.port.out.BankAccountInfo;
import com.minipay.banking.application.port.out.FetchBankAccountInfoPort;
import com.minipay.common.ExternalSystemAdapter;

import java.util.Optional;

@ExternalSystemAdapter
public class BankAccountApiAdapter implements FetchBankAccountInfoPort {

    @Override
    public Optional<BankAccountInfo> fetchBankAccountInfo(String bankName, String accountNumber) {
        // TODO 실제 은행계좌를 HTTP 를 통해 가져오기. 지금은 유효한 계좌정보 리턴하도록 Mocking
        return Optional.of(new BankAccountInfo(bankName, accountNumber, true));
    }
}
