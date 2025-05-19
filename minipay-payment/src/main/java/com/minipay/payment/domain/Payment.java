package com.minipay.payment.domain;

import com.minipay.common.exception.DomainRuleException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

    @EqualsAndHashCode.Include
    private final PaymentId paymentId;
    private final MembershipId buyerId;
    private final MembershipId sellerId;
    private final Money price;
    private final FeeRate feeRate;
    private PaymentStatus paymentStatus;
    private Money paidAmount;
    private Money feeAmount;

    // Factory
    public static Payment newInstance(
            MembershipId buyerId,
            MembershipId sellerId,
            Money price,
            FeeRate feeRate
    ) {
        return new Payment(PaymentId.generate(), buyerId, sellerId, price, feeRate, PaymentStatus.CREATED, null, null);
    }

    public static Payment withId(
            PaymentId paymentId,
            MembershipId buyerId,
            MembershipId sellerId,
            Money price,
            FeeRate feeRate,
            PaymentStatus paymentStatus,
            Money paidAmount,
            Money feeAmount
    ) {
        return new Payment(paymentId, buyerId, sellerId, price, feeRate, paymentStatus, paidAmount, feeAmount);
    }

    // VO
    public record PaymentId(UUID value) {
        public PaymentId {
            if (value == null) {
                throw new DomainRuleException("payment id is null");
            }
        }
        private static PaymentId generate() {
            return new PaymentId(UUID.randomUUID());
        }
    }

    public record MembershipId(UUID value) {
        public MembershipId {
            if (value == null) {
                throw new DomainRuleException("membership id is null");
            }
        }
    }

    public record FeeRate(BigDecimal value) {
        public FeeRate {
            if (value == null) {
                throw new DomainRuleException("fee rate must not be null");
            }
            if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.TEN) > 0) {
                throw new DomainRuleException("fee rate must be between 0 and 1");
            }
        }
    }

    public enum PaymentStatus {
        CREATED,
        PAID,
        OVERDUE
    }

    public void completePayment(Money paidAmount, Money feeAmount) {
        if (this.paymentStatus != PaymentStatus.CREATED) {
            throw new DomainRuleException("Payment is already completed or overdue");
        }
        if (paidAmount == null || feeAmount == null) {
            throw new DomainRuleException("Paid amount and fee amount must not be null");
        }
        if (this.price.subtract(paidAmount).isNegative()) {
            throw new DomainRuleException("Paid amount is less than price");
        }
        this.paymentStatus = PaymentStatus.PAID;
        this.paidAmount = paidAmount;
        this.feeAmount = feeAmount;
    }
}
