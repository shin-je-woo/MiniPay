package com.minipay.banking.application.port.in;


import com.minipay.common.helper.SelfValidating;
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
    String srcBankName;

    @NotNull
    @NotBlank
    String srcAccountNumber;

    @NotNull
    @NotBlank
    String destBankName;

    @NotNull
    @NotBlank
    String destAccountNumber;

    @NotNull
    @Positive
    BigDecimal amount;

    @Builder
    public TransferMoneyCommand(String srcBankName, String srcAccountNumber, String destBankName, String destAccountNumber, BigDecimal amount) {
        this.srcBankName = srcBankName;
        this.srcAccountNumber = srcAccountNumber;
        this.destBankName = destBankName;
        this.destAccountNumber = destAccountNumber;
        this.amount = amount;

        this.validateSelf();
    }
}
