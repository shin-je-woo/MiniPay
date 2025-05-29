package com.minipay.membership.domain;

import com.minipay.common.exception.DomainRuleException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
    private MembershipPassword password;
    private MembershipAddress address;

    // Factory
    public static Membership newInstance(
            Boolean isValid,
            Boolean isCorp,
            MembershipName name,
            MembershipEmail email,
            MembershipPassword password,
            MembershipAddress address
    ) {
        return new Membership(MembershipId.generate(), isValid, isCorp, name, email, password, address);
    }

    public static Membership withId(
            MembershipId membershipId,
            boolean isValid,
            boolean isCorp,
            MembershipName name,
            MembershipEmail email,
            MembershipPassword password,
            MembershipAddress address
    ) {
        return new Membership(membershipId, isValid, isCorp, name, email, password, address);
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

    /**
     * 비밀번호는 10자 이상 16자 이하, 최소 하나의 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.
     */
    public record MembershipRawPassword(String value) {
        public MembershipRawPassword {
            if (!StringUtils.hasText(value)
                    || !value.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\/\\-]).{10,16}$")) {
                throw new DomainRuleException("Password must be 10~16 chars with letters, numbers, and special characters.");
            }
        }

        public String hash(PasswordManager passwordManager) {
            return passwordManager.hash(value);
        }
    }

    public record MembershipPassword(String hashed) {
        public MembershipPassword {
            if (!StringUtils.hasText(hashed)) {
                throw new DomainRuleException("Hashed password cannot be empty");
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
