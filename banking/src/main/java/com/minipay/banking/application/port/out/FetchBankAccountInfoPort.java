package com.minipay.banking.application.port.out;

import java.util.Optional;

public interface FetchBankAccountInfoPort {
    Optional<ExternalBankAccountInfo> fetchBankAccountInfo(String bankName, String accountNumber);
}
