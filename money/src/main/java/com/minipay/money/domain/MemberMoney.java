package com.minipay.money.domain;

import com.minipay.common.event.EventType;
import com.minipay.common.event.Events;
import com.minipay.common.exception.DomainRuleException;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {

    @EqualsAndHashCode.Include
    private final MemberMoneyId memberMoneyId;
    private final MembershipId membershipId;
    private final BankAccountId bankAccountId;
    private Money balance;

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

        Money increaseMoney = computeIncreaseMoney(money);
        MoneyHistory moneyHistory = MoneyHistory.newInstance(
                this.memberMoneyId,
                MoneyHistory.ChangeType.INCREASE,
                increaseMoney
        );

        Events.raise(MemberMoneyEvent.of(EventType.MEMBER_MONEY_INCREASE_REQUESTED, this, moneyHistory));
        return moneyHistory;
    }

    public void increaseBalance(Money money) {
        if (money.isNegative()) {
            throw new DomainRuleException("증액하는 금액은 음수일 수 없습니다.");
        }

        this.balance = this.balance.add(money);
    }

    public void decreaseBalance(Money money) {
        if (money.isNegative()) {
            throw new DomainRuleException("감액하는 금액은 음수일 수 없습니다.");
        }

        this.balance = this.balance.subtract(money);
    }

    /**
     * 머니 충전은 만원 단위로 이루어져야 한다.
     */
    private Money computeIncreaseMoney(Money requested) {
        return requested.divideAndCeiling(new Money(BigDecimal.valueOf(10_000)))
                .multiply(new Money(BigDecimal.valueOf(10_000)));

    }
}
