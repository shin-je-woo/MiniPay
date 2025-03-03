package com.minipay.money.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class IncreaseMoneyCommand extends SelfValidating<IncreaseMoneyCommand> {

    @NotNull
    private final UUID moneyHistoryId;

    @Builder
    public IncreaseMoneyCommand(UUID moneyHistoryId) {
        this.moneyHistoryId = moneyHistoryId;

        this.validateSelf();
    }
}
