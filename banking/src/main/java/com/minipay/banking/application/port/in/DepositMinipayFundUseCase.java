package com.minipay.banking.application.port.in;

public interface DepositMinipayFundUseCase {
    void deposit(DepositMinipayMoneyCommand command);
}
