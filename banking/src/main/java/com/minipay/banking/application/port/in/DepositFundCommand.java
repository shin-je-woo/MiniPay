package com.minipay.banking.application.port.in;

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
public class DepositFundCommand extends SelfValidating<DepositFundCommand> {

    @NotNull
    private final UUID bankAccountId;

    @NotNull
    private final UUID moneyHistoryId;

    @NotNull
    @Positive
    private final BigDecimal amount;

    @Builder
    public DepositFundCommand(UUID bankAccountId, UUID moneyHistoryId, BigDecimal amount) {
        this.bankAccountId = bankAccountId;
        this.moneyHistoryId = moneyHistoryId;
        this.amount = amount;

        this.validateSelf();
    }
}