package com.minipay.banking.application.port.in;

import com.minipay.banking.application.port.out.FirmBankingResult;
import com.minipay.banking.domain.event.WithdrawalFundCreatedEvent;

public interface WithdrawalFundUseCase {
    void withdrawal(WithdrawalFundCommand command);
    void withdrawalByAxon(WithdrawalFundByAxonCommand command);
    FirmBankingResult processWithdrawalByAxon(WithdrawalFundCreatedEvent event);
}
