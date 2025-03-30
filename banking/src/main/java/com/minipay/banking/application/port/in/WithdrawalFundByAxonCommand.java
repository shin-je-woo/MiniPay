package com.minipay.banking.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class WithdrawalFundByAxonCommand extends SelfValidating<WithdrawalFundByAxonCommand> {

    @NotNull
    private final UUID bankAccountId;

    @NotNull
    @NotBlank
    private final String bankName;

    @NotNull
    @NotBlank
    private final String bankAccountNumber;

    @NotNull
    @Positive
    private final BigDecimal amount;

    @Builder
    public WithdrawalFundByAxonCommand(UUID bankAccountId, String bankName, String bankAccountNumber, BigDecimal amount) {
        this.bankAccountId = bankAccountId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.amount = amount;

        this.validateSelf();
    }
}