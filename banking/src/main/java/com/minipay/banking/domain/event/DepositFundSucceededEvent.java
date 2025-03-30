package com.minipay.banking.domain.event;

import java.util.UUID;

public record DepositFundSucceededEvent(
        UUID fundTransactionId
) {
}
