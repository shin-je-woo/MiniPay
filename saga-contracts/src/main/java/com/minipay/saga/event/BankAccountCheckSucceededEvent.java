package com.minipay.saga.event;

import java.util.UUID;

public record BankAccountCheckSucceededEvent(
        UUID checkLinkedBankAccountId,
        UUID rechargeRequestId,
        UUID bankAccountId,
        UUID memberMoneyId,
        UUID moneyHistoryId
) {
}
