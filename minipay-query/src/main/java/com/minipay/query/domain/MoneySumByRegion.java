package com.minipay.query.domain;

import com.minipay.common.exception.DomainRuleException;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class MoneySumByRegion {

    @EqualsAndHashCode.Include
    private final MoneySumByRegionId moneySumByRegionId;
    private final RegionName regionName;
    private final MoneySum moneySum;

    // Factory
    public static MoneySumByRegion newInstance(
            RegionName regionName,
            MoneySum moneySum
    ) {
        return new MoneySumByRegion(MoneySumByRegionId.generate(), regionName, moneySum);
    }

    // VO
    public record MoneySumByRegionId(UUID value) {
        public MoneySumByRegionId {
            if (value == null) {
                throw new DomainRuleException("MoneySumByRegionId is null");
            }
        }

        private static MoneySumByRegionId generate() {
            return new MoneySumByRegionId(UUID.randomUUID());
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
