package com.minipay.banking.domain;

import com.minipay.common.event.EventType;
import com.minipay.common.event.Events;
import com.minipay.common.exception.DomainRuleException;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Getter
public class BankAccount {

    private final UUID uuid;
    private final BankAccountId bankAccountId;
    private final OwnerId ownerId;
    private final LinkedBankAccount linkedBankAccount;

    // Constructor
    private BankAccount(BankAccountId bankAccountId, OwnerId ownerId, LinkedBankAccount linkedBankAccount) {
        this.uuid = UUID.randomUUID();
        this.bankAccountId = bankAccountId;
        this.ownerId = ownerId;
        this.linkedBankAccount = linkedBankAccount;

        Events.raise(BankAccountEvent.of(EventType.BANK_ACCOUNT_CREATED, this));
    }

    private BankAccount(UUID uuid, BankAccountId bankAccountId, OwnerId ownerId, LinkedBankAccount linkedBankAccount) {
        this.uuid = uuid;
        this.bankAccountId = bankAccountId;
        this.ownerId = ownerId;
        this.linkedBankAccount = linkedBankAccount;
    }

    // Factory
    public static BankAccount create(
            OwnerId ownerId,
            LinkedBankAccount linkedBankAccount
    ) {
        return new BankAccount(null, ownerId, linkedBankAccount);
    }

    public static BankAccount withId(
            UUID uuid,
            BankAccountId bankAccountId,
            OwnerId ownerId,
            LinkedBankAccount linkedBankAccount
    ) {
        return new BankAccount(uuid, bankAccountId, ownerId, linkedBankAccount);
    }

    // VO
    public record BankAccountId(Long value) {
        public BankAccountId {
            if (value == null) {
                throw new DomainRuleException("bank account id is null");
            }
        }
    }

    public record OwnerId(Long value) {
        public OwnerId {
            if (value == null) {
                throw new DomainRuleException("owner id is null");
            }
        }
    }

    public record LinkedBankAccount(String bankName, String accountNumber, boolean linkedStatusIsValid) {
        public LinkedBankAccount {
            if (!StringUtils.hasText(bankName) || !StringUtils.hasText(accountNumber)) {
                throw new DomainRuleException("bankName or accountNumber is empty");
            }
            if (!accountNumber.matches("\\d{10,16}")) {
                throw new DomainRuleException("invalid bank account number");
            }
        }
    }
}
