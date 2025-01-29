package com.minipay.banking.application.port.out;

import com.minipay.banking.domain.TransferMoney;

public interface ModifyTransferMoneyPort {
    TransferMoney modifyTransferMoney(TransferMoney transferMoney);
}
