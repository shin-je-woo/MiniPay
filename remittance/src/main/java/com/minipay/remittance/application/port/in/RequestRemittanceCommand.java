package com.minipay.remittance.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RequestRemittanceCommand extends SelfValidating<RequestRemittanceCommand> {

    @NotNull
    private final UUID senderId;

    private final UUID recipientId;

    private final String destBankName;

    private final String destBankAccountNumber;

    @NotNull
    @Positive
    private final BigDecimal amount;

    @Builder
    public RequestRemittanceCommand(UUID senderId, UUID recipientId, String destBankName, String destBankAccountNumber, BigDecimal amount) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.destBankName = destBankName;
        this.destBankAccountNumber = destBankAccountNumber;
        this.amount = amount;

        this.validateSelf();
    }
}
