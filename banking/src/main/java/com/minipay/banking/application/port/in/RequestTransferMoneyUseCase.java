package com.minipay.banking.application.port.in;

import com.minipay.banking.domain.TransferMoney;

public interface RequestTransferMoneyUseCase {
    TransferMoney requestTransferMoney(TransferMoneyCommand command);
}
