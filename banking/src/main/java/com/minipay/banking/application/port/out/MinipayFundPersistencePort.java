package com.minipay.banking.application.port.out;

import com.minipay.banking.domain.model.MinipayFund;

public interface MinipayFundPersistencePort {
    void storeMinipayFund(MinipayFund minipayFund);
}
