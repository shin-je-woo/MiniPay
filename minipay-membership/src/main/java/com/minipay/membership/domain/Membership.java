package com.minipay.membership.domain;

import com.minipay.common.exception.DomainRuleException;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership {

    @EqualsAndHashCode.Include
    private final MembershipId membershipId;
    private final boolean isValid;
    private final boolean isCorp;
    private MembershipName name;
    private MembershipEmail email;
    private MembershipAddress address;

    // Factory
    public static Membership newInstance(
            Boolean isValid,
            Boolean isCorp,
            MembershipName name,
            MembershipEmail email,
            MembershipAddress address
    ) {
        return new Membership(MembershipId.generate(), isValid, isCorp, name, email, address);
    }

    public static Membership withId(
            MembershipId membershipId,
            boolean isValid,
            boolean isCorp,
            MembershipName name,
            MembershipEmail email,
            MembershipAddress address
    ) {
        return new Membership(membershipId, isValid, isCorp, name, email, address);
    }

    //VO
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

    public void changeInfo(MembershipName name, MembershipEmail email, MembershipAddress address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
