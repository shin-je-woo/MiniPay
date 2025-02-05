package com.minipay.banking.domain;

import com.minipay.common.exception.DomainRuleException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankAccount {

    private final BankAccountId bankAccountId;
    private final OwnerId ownerId;
    private final LinkedBankAccount linkedBankAccount;

    public static BankAccount create(
            OwnerId ownerId,
            LinkedBankAccount linkedBankAccount
    ) {
        return new BankAccount(null, ownerId, linkedBankAccount);
    }

    public static BankAccount withId(
            BankAccountId bankAccountId,
            OwnerId ownerId,
            LinkedBankAccount linkedBankAccount
    ) {
        return new BankAccount(bankAccountId, ownerId, linkedBankAccount);
    }

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
