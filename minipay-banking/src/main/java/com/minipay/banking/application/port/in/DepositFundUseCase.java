package com.minipay.banking.application.port.in;

import com.minipay.banking.application.port.out.FirmBankingResult;
import com.minipay.saga.event.DepositFundCreatedEvent;

public interface DepositFundUseCase {
    void deposit(DepositFundCommand command);
    FirmBankingResult depositBySaga(DepositFundCreatedEvent event);
}
