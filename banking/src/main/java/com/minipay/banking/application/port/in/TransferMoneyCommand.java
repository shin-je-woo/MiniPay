package com.minipay.banking.application.port.in;


import com.minipay.common.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TransferMoneyCommand extends SelfValidating<TransferMoneyCommand> {

    @NotNull
    @NotBlank
    String fromBankName;

    @NotNull
    @NotBlank
    String fromBankAccountNumber;

    @NotNull
    @NotBlank
    String toBankName;

    @NotNull
    @NotBlank
    String toBankAccountNumber;

    @NotNull
    @Positive
    BigDecimal moneyAmount;

    @Builder
    public TransferMoneyCommand(String fromBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, BigDecimal moneyAmount) {
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;

        this.validateSelf();
    }
}
