package com.minipay.remittance.application.port.out;

import java.math.BigDecimal;
import java.util.UUID;

public interface BankingServicePort {
    boolean withdrawalFund(UUID senderBankAccountId, String bankName, String bankAccountNumber, BigDecimal amount);
}
