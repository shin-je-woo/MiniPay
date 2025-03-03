package com.minipay.money.domain;

import com.minipay.common.event.EventType;
import com.minipay.common.event.Events;
import com.minipay.common.exception.DomainRuleException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {

    private final MemberMoneyId memberMoneyId;
    private final MembershipId membershipId;
    private final BankAccountId bankAccountId;
    private final Money balance;

    // Factory
    public static MemberMoney newInstance(
            MembershipId membershipId,
            BankAccountId bankAccountId
    ) {
        return new MemberMoney(MemberMoneyId.generate(), membershipId, bankAccountId, Money.ZERO);
    }

    public static MemberMoney withId(
            MemberMoneyId memberMoneyId,
            MembershipId membershipId,
            BankAccountId bankAccountId,
            Money balance
    ) {
        return new MemberMoney(memberMoneyId, membershipId, bankAccountId, balance);
    }

    // VO
    public record MemberMoneyId(UUID value) {
        public MemberMoneyId {
            if (value == null) {
                throw new DomainRuleException("member money id is null");
            }
        }

        private static MemberMoneyId generate() {
            return new MemberMoneyId(UUID.randomUUID());
        }
    }

    public record MembershipId(UUID value) {
        public MembershipId {
            if (value == null) {
                throw new DomainRuleException("membership id is null");
            }
        }
    }

    public record BankAccountId(UUID value) {
        public BankAccountId {
            if (value == null) {
                throw new DomainRuleException("bank account id is null");
            }
        }
    }

    // Logic
    public MoneyHistory requestIncreaseMoney(Money money) {
        if (money.isNegative()) {
            throw new DomainRuleException("증액하려는 금액은 음수일 수 없습니다.");
        }

        MoneyHistory moneyHistory = MoneyHistory.newInstance(
                this.memberMoneyId,
                MoneyHistory.ChangeType.INCREASE,
                money
        );

        Events.raise(MemberMoneyEvent.of(EventType.MEMBER_MONEY_INCREASE_REQUESTED, this, moneyHistory));
        return moneyHistory;
    }

    public MemberMoney increaseBalance(Money money) {
        if (money == null || money.isNegative()) {
            throw new DomainRuleException("money is null or negative");
        }

        Money newMoney = this.balance.add(money);

        return new MemberMoney(this.memberMoneyId, this.membershipId, this.bankAccountId, newMoney);
    }
}
