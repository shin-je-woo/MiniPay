package com.minipay.remittance.domain;

import com.minipay.common.exception.DomainRuleException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Remittance {

    @EqualsAndHashCode.Include
    private final RemittanceId remittanceId;
    private final Sender sender;
    private final Recipient recipient;
    private final RemittanceType remittanceType;
    private final Money amount;
    private RemittanceStatus remittanceStatus;

    // Factory
    public static Remittance newInstance(
            Sender sender,
            Recipient recipient,
            Money amount
    ) {
        return new Remittance(RemittanceId.generate(), sender, recipient, recipient.getRemittanceType(), amount, RemittanceStatus.REQUESTED);
    }

    // VO
    public record RemittanceId(UUID value) {
        public RemittanceId {
            if (value == null) {
                throw new IllegalArgumentException("RemittanceId cannot be null");
            }
        }

        private static RemittanceId generate() {
            return new RemittanceId(UUID.randomUUID());
        }
    }

    public record Sender(
            UUID membershipId
    ) {
        public Sender {
            if (membershipId == null) {
                throw new IllegalArgumentException("Sender cannot be null");
            }
        }
    }

    public record Recipient(
            UUID membershipId,
            String bankName,
            String accountNumber
    ) {
        public Recipient {
            // membershipId가 있으면 내부고객, 없으면 외부은행
            if (membershipId == null && (bankName == null || accountNumber == null)) {
                throw new IllegalArgumentException("Invalid Recipient");
            }
        }

        private RemittanceType getRemittanceType() {
            return membershipId == null ? RemittanceType.EXTERNAL : RemittanceType.INTERNAL;
        }
    }

    public enum RemittanceType {
        INTERNAL,
        EXTERNAL
    }

    public enum RemittanceStatus {
        REQUESTED,
        SUCCEEDED,
        FAILED
    }

    // Logic
    public void success() {
        if (this.getRemittanceStatus() != RemittanceStatus.REQUESTED) {
            throw new DomainRuleException("Remittance status is not REQUESTED");
        }
        this.remittanceStatus = RemittanceStatus.SUCCEEDED;
    }

    public void fail() {
        if (this.getRemittanceStatus() != RemittanceStatus.REQUESTED) {
            throw new DomainRuleException("Remittance status is not REQUESTED");
        }
        this.remittanceStatus = RemittanceStatus.FAILED;
    }
}
