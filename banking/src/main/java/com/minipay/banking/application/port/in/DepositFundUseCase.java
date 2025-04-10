package com.minipay.banking.application.port.in;

import com.minipay.banking.application.port.out.FirmBankingResult;
import com.minipay.banking.domain.event.DepositFundCreatedEvent;
import com.minipay.saga.command.OrderDepositFundCommand;

public interface DepositFundUseCase {
    void deposit(DepositFundCommand command);
    void depositByAxon(DepositFundByAxonCommand command);
    FirmBankingResult processDepositByAxon(DepositFundCreatedEvent event);
    void depositByAxonSaga(OrderDepositFundCommand command);
}
