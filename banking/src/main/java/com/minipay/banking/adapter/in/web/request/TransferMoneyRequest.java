package com.minipay.banking.adapter.in.web.request;

import java.math.BigDecimal;

public record TransferMoneyRequest(
        String fromBankName,
        String fromBankAccountNumber,
        String toBankName,
        String toBankAccountNumber,
        BigDecimal moneyAmount
) {
}
