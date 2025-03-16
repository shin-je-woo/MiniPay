package com.minipay.banking.application.port.in;

public interface WithdrawalMinipayFundUseCase {
    void withdrawal(WithdrawalMinipayMoneyCommand command);
}
