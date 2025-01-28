package com.minipay.banking.application.port.in;

import com.minipay.common.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RegisterBankAccountCommand extends SelfValidating<RegisterBankAccountCommand> {

    @NotNull
    private final Long ownerId;

    @NotNull
    @NotBlank
    private final String bankName;

    @NotNull
    @NotBlank
    private final String bankAccountNumber;

    @Builder
    public RegisterBankAccountCommand(Long ownerId, String bankName, String bankAccountNumber) {
        this.ownerId = ownerId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;

        this.validateSelf();
    }
}
