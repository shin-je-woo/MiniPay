package com.minipay.money.domain;

import com.minipay.common.DomainRuleException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {

    private final MemberMoneyId memberMoneyId;
    private final MembershipId membershipId;
    private final BankAccountId bankAccountId;
    private final Money balance;

    public static MemberMoney create(
            MembershipId membershipId,
            BankAccountId bankAccountId
    ) {
        return new MemberMoney(null, membershipId, bankAccountId, Money.ZERO);
    }

    public static MemberMoney withId(
            MemberMoneyId memberMoneyId,
            MembershipId membershipId,
            BankAccountId bankAccountId,
            Money balance
    ) {
        return new MemberMoney(memberMoneyId, membershipId, bankAccountId, balance);
    }

    public record MemberMoneyId(Long value) {
        public MemberMoneyId {
            if (value == null) {
                throw new DomainRuleException("member money id is null");
            }
        }
    }

    public record MembershipId(Long value) {
        public MembershipId {
            if (value == null) {
                throw new DomainRuleException("membership id is null");
            }
        }
    }

    public record BankAccountId(Long value) {
        public BankAccountId {
            if (value == null) {
                throw new DomainRuleException("bank account id is null");
            }
        }
    }
}
