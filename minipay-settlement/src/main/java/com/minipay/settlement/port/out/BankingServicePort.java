package com.minipay.settlement.port.out;

import java.math.BigDecimal;
import java.util.UUID;

public interface BankingServicePort {
    BankAccountInfo getBankAccountInfo(UUID bankAccountId);
    boolean transferSettlementAmount(UUID bankAccountId, String bankName, String bankAccountNumber, BigDecimal amount);
}