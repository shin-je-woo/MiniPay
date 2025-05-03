package com.minipay.money.application.port.out;

import java.util.List;

public interface BankingServicePort {
    List<BankAccountInfo> getBankAccountsByBankName(String bankName);
}
