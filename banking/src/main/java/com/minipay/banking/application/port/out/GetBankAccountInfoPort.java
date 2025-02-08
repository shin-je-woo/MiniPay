package com.minipay.banking.application.port.out;

public interface GetBankAccountInfoPort {
    ExternalBankAccountInfo getBankAccountInfo(String bankName, String accountNumber);
}
