package com.minipay.payment.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CreatePaymentCommand extends SelfValidating<CreatePaymentCommand> {

    @NotNull
    private final UUID buyerId;

    @NotNull
    private final UUID sellerId;

    @NotNull
    @Positive
    private final BigDecimal price;

    @NotNull
    @Positive
    private final BigDecimal feeRate;

    public CreatePaymentCommand(UUID buyerId, UUID sellerId, BigDecimal price, BigDecimal feeRate) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
        this.feeRate = feeRate;

        this.validateSelf();
    }
}
