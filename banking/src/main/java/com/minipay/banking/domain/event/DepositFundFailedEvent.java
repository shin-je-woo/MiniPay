package com.minipay.banking.domain.event;

import java.util.UUID;

public record DepositFundFailedEvent(
        UUID fundTransactionId
) {
}
