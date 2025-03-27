package com.minipay.banking.domain.model;

import com.minipay.banking.domain.event.BankAccountEvent;
import com.minipay.common.event.EventType;
import com.minipay.common.event.Events;
import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankAccount {

    @EqualsAndHashCode.Include
    private final BankAccountId bankAccountId;
    private final MembershipId membershipId;
    private final ExternalBankAccount linkedBankAccount;
    private final LinkedStatus linkedStatus;

    // Factory
    public static BankAccount newInstance(
            MembershipId membershipId,
            ExternalBankAccount externalBankAccount
    ) {
        BankAccount bankAccount = new BankAccount(BankAccountId.generate(), membershipId, externalBankAccount, LinkedStatus.VALID);
        Events.raise(BankAccountEvent.of(EventType.BANK_ACCOUNT_CREATED, bankAccount));

        return bankAccount;
    }

    public static BankAccount withId(
            BankAccountId bankAccountId,
            MembershipId membershipId,
            ExternalBankAccount externalBankAccount,
            LinkedStatus linkedStatus
    ) {
        return new BankAccount(bankAccountId, membershipId, externalBankAccount, linkedStatus);
    }

    // VO
    public record BankAccountId(UUID value) {
        public BankAccountId {
            if (value == null) {
                throw new IllegalArgumentException("BankAccountId cannot be null");
            }
        }

        private static BankAccountId generate() {
            return new BankAccountId(UUID.randomUUID());
        }
    }

    public record MembershipId(UUID value) {
        public MembershipId {
            if (value == null) {
                throw new IllegalArgumentException("MembershipId cannot be null");
            }
        }
    }

    public enum LinkedStatus {
        VALID, INVALID
    }
}
