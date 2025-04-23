package com.minipay.query.application.service;

import com.minipay.common.annotation.Query;
import com.minipay.query.application.port.in.GetMoneySumAggregationUseCase;
import com.minipay.query.application.port.in.GetMoneySumByAddressQuery;
import com.minipay.query.application.port.out.MembershipServicePort;
import com.minipay.query.application.port.out.MoneyInfo;
import com.minipay.query.application.port.out.MoneyServicePort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Query
@RequiredArgsConstructor
public class GetMoneySumAggregationService implements GetMoneySumAggregationUseCase {

    private final MembershipServicePort membershipServicePort;
    private final MoneyServicePort moneyServicePort;
    private static final int BATCH_SIZE = 100;

    /**
     * API Aggregation 방식으로 주소에 해당하는 회원의 잔액을 모두 합산하여 반환한다.
     * Money 서비스 부하를 막기 위해 배치 단위로 호출한다.
     */
    @Override
    public BigDecimal getMoneySumByAddress(GetMoneySumByAddressQuery query) {
        List<UUID> membershipIds = membershipServicePort.getMembershipIdsByAddress(query.getAddress());

        return IntStream.range(0, (membershipIds.size() + BATCH_SIZE - 1) / BATCH_SIZE)
                .mapToObj(batchIndex -> {
                    int fromIndex = batchIndex * BATCH_SIZE;
                    int toIndex = Math.min(fromIndex + BATCH_SIZE, membershipIds.size());
                    List<UUID> batchList = membershipIds.subList(fromIndex, toIndex);
                    return moneyServicePort.getMoneyInfosByMembershipIds(batchList);
                })
                .flatMap(List::stream)
                .map(MoneyInfo::balance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
