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
public class DecreaseMoneyAfterBankingCommand extends SelfValidating<DecreaseMoneyAfterBankingCommand> {

    @NotNull
    private final UUID bankAccountId;

    @NotNull
    @Positive
    private final BigDecimal amount;

    @Builder
    public DecreaseMoneyAfterBankingCommand(UUID bankAccountId, BigDecimal amount) {
        this.bankAccountId = bankAccountId;
        this.amount = amount;

        this.validateSelf();
    }
}
