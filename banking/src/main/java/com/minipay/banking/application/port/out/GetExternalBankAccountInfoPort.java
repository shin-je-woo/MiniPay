package com.minipay.banking.application.port.out;

public interface GetExternalBankAccountInfoPort {
    ExternalBankAccountInfo getBankAccountInfo(String bankName, String accountNumber);
}
