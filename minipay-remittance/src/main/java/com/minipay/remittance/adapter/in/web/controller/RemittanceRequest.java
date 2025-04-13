package com.minipay.remittance.adapter.in.web.controller;

import java.math.BigDecimal;
import java.util.UUID;

public record RemittanceRequest(
        UUID senderId,
        UUID recipientId,
        String destBankName,
        String destBankAccountNumber,
        BigDecimal amount
) {
}
