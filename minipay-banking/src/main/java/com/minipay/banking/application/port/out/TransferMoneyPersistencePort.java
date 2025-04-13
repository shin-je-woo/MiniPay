package com.minipay.banking.application.port.out;

import com.minipay.banking.domain.model.TransferMoney;

public interface TransferMoneyPersistencePort {
    TransferMoney createTransferMoney(TransferMoney transferMoney);
    TransferMoney updateTransferMoney(TransferMoney transferMoney);
}
