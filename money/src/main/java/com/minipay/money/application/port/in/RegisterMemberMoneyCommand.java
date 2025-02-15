package com.minipay.money.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RegisterMemberMoneyCommand extends SelfValidating<RegisterMemberMoneyCommand> {

    @NotNull
    private final UUID membershipId;

    @NotNull
    private final UUID bankAccountId;

    @Builder
    public RegisterMemberMoneyCommand(UUID membershipId, UUID bankAccountId) {
        this.membershipId = membershipId;
        this.bankAccountId = bankAccountId;

        this.validateSelf();
    }
}
