package com.minipay.banking.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferMoney {

    @EqualsAndHashCode.Include
    private final TransferMoneyId transferMoneyId;
    private final ExternalBankAccount sourceAccount;
    private final ExternalBankAccount destinationAccount;
    private final Money amount;
    private TransferMoneyStatus status;

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
                throw new IllegalArgumentException("Transfer money id is null");
            }
        }

        private static TransferMoneyId generate() {
            return new TransferMoneyId(UUID.randomUUID());
        }
    }

    public enum TransferMoneyStatus {
        REQUESTED,
        SUCCEEDED,
        FAILED
    }

    // Logic
    public void changeStatus(TransferMoneyStatus status) {
        this.status = status;
    }
}
