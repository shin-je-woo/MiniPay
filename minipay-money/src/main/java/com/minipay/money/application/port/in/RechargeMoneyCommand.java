package com.minipay.money.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RechargeMoneyCommand extends SelfValidating<RechargeMoneyCommand> {

    @NotNull
    private final UUID moneyHistoryId;

    @Builder
    public RechargeMoneyCommand(UUID moneyHistoryId) {
        this.moneyHistoryId = moneyHistoryId;

        this.validateSelf();
    }
}
