package com.minipay.money.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RegisterMemberMoneyCommand extends SelfValidating<RegisterMemberMoneyCommand> {

    @NotNull
    private final Long membershipId;

    @NotNull
    private final Long bankAccountId;

    @Builder
    public RegisterMemberMoneyCommand(Long membershipId, Long bankAccountId) {
        this.membershipId = membershipId;
        this.bankAccountId = bankAccountId;

        this.validateSelf();
    }
}
