package com.minipay.banking.application.port.out;

import com.minipay.banking.domain.TransferMoney;

public record FirmBankingResult(
        TransferMoney.TransferMoneyStatus transferMoneyStatus
) {
}
