package com.minipay.query.domain;

import com.minipay.common.exception.DomainRuleException;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class MoneySumByMembership {

    @EqualsAndHashCode.Include
    private final MoneySumByMembershipId moneySumByMembershipId;
    private final MembershipId membershipId;
    private final RegionName regionName;
    private final MoneySum moneySum;

    // Factory
    public static MoneySumByMembership newInstance(
            MembershipId membershipId,
            RegionName regionName,
            MoneySum moneySum
    ) {
        return new MoneySumByMembership(MoneySumByMembershipId.generate(), membershipId, regionName, moneySum);
    }

    // VO
    public record MoneySumByMembershipId(UUID value) {
        public MoneySumByMembershipId {
            if (value == null) {
                throw new DomainRuleException("MoneySumByMembershipId is null");
            }
        }

        private static MoneySumByMembershipId generate() {
            return new MoneySumByMembershipId(UUID.randomUUID());
        }
    }

    public record MembershipId(UUID value) {
        public MembershipId {
            if (value == null) {
                throw new DomainRuleException("MembershipId is null");
            }
        }
    }

    public record RegionName(String value) {
        public RegionName {
            if (value == null) {
                throw new DomainRuleException("RegionName is null");
            }
        }
    }

    public record MoneySum(BigDecimal value) {
        public MoneySum {
            if (value == null) {
                throw new DomainRuleException("MoneySum is null");
            }
        }
    }
}
