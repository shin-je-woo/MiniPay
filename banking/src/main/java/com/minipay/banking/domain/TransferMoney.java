package com.minipay.banking.domain;

import com.minipay.common.exception.DomainRuleException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferMoney {

    private final TransferMoneyId transferMoneyId;
    private final ExternalBankAccount sourceAccount;
    private final ExternalBankAccount destinationAccount;
    private final Money amount;
    private final TransferMoneyStatus status;

    // Factory
    public static TransferMoney newInstance(
            ExternalBankAccount sourceAccount,
            ExternalBankAccount destinationAccount,
            Money amount
    ) {
        return new TransferMoney(TransferMoneyId.generate(), sourceAccount, destinationAccount, amount, TransferMoneyStatus.REQUESTED);
    }

    public static TransferMoney withId(
            TransferMoneyId transferMoneyId,
            ExternalBankAccount sourceAccount,
            ExternalBankAccount destinationAccount,
            Money amount,
            TransferMoneyStatus status
    ) {
        return new TransferMoney(transferMoneyId, sourceAccount, destinationAccount, amount, status);
    }

    // VO
    public record TransferMoneyId(UUID value) {
        public TransferMoneyId {
            if (value == null) {
                throw new DomainRuleException("transfer money id is null");
            }
        }

        private static TransferMoneyId generate() {
            return new TransferMoneyId(UUID.randomUUID());
        }
    }

    public enum TransferMoneyStatus {
        REQUESTED,
        SUCCESS,
        FAILED
    }

    // Logic
    public TransferMoney changeStatus(TransferMoneyStatus status) {
        if (status == null) {
            throw new DomainRuleException("status can't be null");
        }
        return new TransferMoney(this.transferMoneyId, this.sourceAccount, this.destinationAccount, this.amount, status);
    }
}
