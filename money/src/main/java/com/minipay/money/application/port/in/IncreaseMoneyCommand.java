package com.minipay.money.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode(callSuper = false)
public class IncreaseMoneyCommand extends SelfValidating<IncreaseMoneyCommand> {

    @NotNull
    private final Long memberMoneyId;

    @NotNull
    @Positive
    private final BigDecimal amount;

    @Builder
    public IncreaseMoneyCommand(Long memberMoneyId, BigDecimal amount) {
        this.memberMoneyId = memberMoneyId;
        this.amount = amount;

        this.validateSelf();
    }
}
