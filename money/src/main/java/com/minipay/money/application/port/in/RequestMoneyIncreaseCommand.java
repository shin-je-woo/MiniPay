package com.minipay.money.application.port.in;

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
public class RequestMoneyIncreaseCommand extends SelfValidating<RequestMoneyIncreaseCommand> {

    @NotNull
    private final UUID memberMoneyId;

    @NotNull
    @Positive
    private final BigDecimal amount;

    @Builder
    public RequestMoneyIncreaseCommand(UUID memberMoneyId, BigDecimal amount) {
        this.memberMoneyId = memberMoneyId;
        this.amount = amount;

        this.validateSelf();
    }
}
