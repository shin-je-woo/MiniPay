package com.minipay.banking.application.port.out;

public interface ExternalBankingPort {
    ExternalBankAccountInfo getBankAccountInfo(String bankName, String accountNumber);
    FirmBankingResult requestFirmBanking(FirmBankingRequest firmBankingRequest);
}
