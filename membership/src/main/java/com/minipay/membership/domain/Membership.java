package com.minipay.membership.domain;

import com.minipay.common.exception.DomainRuleException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership {

    private final MembershipId membershipId;
    private final MembershipName name;
    private final MembershipEmail email;
    private final MembershipAddress address;
    private final boolean isValid;
    private final boolean isCorp;

    public static Membership newInstance(
            MembershipName name,
            MembershipEmail email,
            MembershipAddress address,
            boolean isValid,
            boolean isCorp
    ) {
        return new Membership(MembershipId.generate(), name, email, address, isValid, isCorp);
    }

    public static Membership withId(
            MembershipId membershipId,
            MembershipName name,
            MembershipEmail email,
            MembershipAddress address,
            boolean isValid,
            boolean isCorp
    ) {
        return new Membership(membershipId, name, email, address, isValid, isCorp);
    }

    public Membership changeInfo(
            MembershipName name,
            MembershipEmail email,
            MembershipAddress address
    ) {
        return new Membership(this.membershipId, name, email, address, this.isValid, this.isCorp);
    }

    public record MembershipId(UUID value) {
        public MembershipId {
            if (value == null) {
                throw new IllegalArgumentException("MembershipId is null");
            }
        }

        private static MembershipId generate() {
            return new MembershipId(UUID.randomUUID());
        }
    }

    public record MembershipName(String value) {
        public MembershipName {
            if (!StringUtils.hasText(value) || value.length() > 20) {
                throw new DomainRuleException("membershipName is empty or too long");
            }
        }
    }

    public record MembershipEmail(String value) {
        public MembershipEmail {
            if (!StringUtils.hasText(value) || !value.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                throw new DomainRuleException("membershipEmail value is not matched email format");
            }
        }
    }

    public record MembershipAddress(String value) {
        public MembershipAddress {
            if (!StringUtils.hasText(value) || value.length() > 50) {
                throw new DomainRuleException("membershipAddress value is empty or too long");
            }
        }
    }
}
