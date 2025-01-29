package com.minipay.banking.application.port.out;

import com.minipay.banking.domain.TransferMoney;

public interface CreateTransferMoneyPort {
    TransferMoney createTransferMoney(TransferMoney transferMoney);
}
