package com.minipay.remittance.application.port.out;

import java.math.BigDecimal;

public interface BankingServicePort {
    boolean withdrawalMinipayFund(String bankName, String bankAccountNumber, BigDecimal amount);
}
