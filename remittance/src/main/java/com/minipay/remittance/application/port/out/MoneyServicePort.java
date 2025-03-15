package com.minipay.remittance.application.port.out;

import java.math.BigDecimal;
import java.util.UUID;

public interface MoneyServicePort {
    MoneyInfo getMoneyInfo(UUID membershipId);
    boolean rechargeMoney(UUID memberMoneyId, BigDecimal amount);
    boolean decreaseMoney(UUID memberMoneyId, BigDecimal amount);
    boolean increaseMoney(UUID memberMoneyId, BigDecimal amount);
}
