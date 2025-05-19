package com.minipay.payment.application.port.in;

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
public class CompletePaymentCommand extends SelfValidating<CompletePaymentCommand> {

    @NotNull
    private final UUID paymentId;

    @NotNull
    @Positive
    private final BigDecimal paidAmount;

    @NotNull
    @Positive
    private final BigDecimal feeAmount;

    @Builder
    public CompletePaymentCommand(UUID paymentId, BigDecimal paidAmount, BigDecimal feeAmount) {
        this.paymentId = paymentId;
        this.paidAmount = paidAmount;
        this.feeAmount = feeAmount;

        this.validateSelf();
    }
}
