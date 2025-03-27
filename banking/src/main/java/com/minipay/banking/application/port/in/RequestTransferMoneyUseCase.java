package com.minipay.banking.application.port.in;

import com.minipay.banking.domain.model.TransferMoney;

public interface RequestTransferMoneyUseCase {
    TransferMoney requestTransferMoney(TransferMoneyCommand command);
}
