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
            BankAccountId bankAccountId,
            Money balance
    ) {
        return new MemberMoney(null, membershipId, bankAccountId, balance);
    }

    public static MemberMoney withId(
            MemberMoneyId memberMoneyId,
            MembershipId membershipId,
            BankAccountId bankAccountId,
            Money balance
    ) {
        return new MemberMoney(memberMoneyId, membershipId, bankAccountId, balance);
    }

    public MemberMoney increaseBalance(Money money) {
        Money newMoney = this.balance.add(money);
        return new MemberMoney(this.memberMoneyId, this.membershipId, this.bankAccountId, newMoney);
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
