package com.minipay.saga.event;

import java.util.UUID;

public record BankAccountCheckFailedEvent(
        UUID checkLinkedBankAccountId,
        UUID rechargeRequestId,
        UUID bankAccountId,
        UUID memberMoneyId,
        UUID moneyHistoryId
) {
}
