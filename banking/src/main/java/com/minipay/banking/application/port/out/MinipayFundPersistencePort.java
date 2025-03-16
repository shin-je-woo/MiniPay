package com.minipay.banking.application.port.out;

import com.minipay.banking.domain.MinipayFund;

public interface MinipayFundPersistencePort {
    void storeMinipayFund(MinipayFund minipayFund);
}
