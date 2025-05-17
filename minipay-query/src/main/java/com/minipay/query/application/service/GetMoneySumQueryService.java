package com.minipay.query.application.service;

import com.minipay.common.annotation.Query;
import com.minipay.query.adapter.in.axon.dto.QueryDailyMoneySumByAddress;
import com.minipay.query.adapter.in.axon.dto.QueryMoneySumByAddress;
import com.minipay.query.adapter.in.axon.dto.QueryTopMoneySumByMembership;
import com.minipay.query.application.port.out.MoneySumByMembershipsResult;
import com.minipay.query.application.port.in.GetDailyMoneySumByAddressQuery;
import com.minipay.query.application.port.in.GetMoneySumByAddressQuery;
import com.minipay.query.application.port.in.GetMoneySumQueryUseCase;
import com.minipay.query.application.port.in.GetTopMoneySumByMembershipQuery;
import com.minipay.query.domain.MoneySumByMembership;
import com.minipay.query.domain.MoneySumByRegion;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import java.util.List;

@Query
@RequiredArgsConstructor
public class GetMoneySumQueryService implements GetMoneySumQueryUseCase {

    private final QueryGateway queryGateway;

    @Override
    public MoneySumByRegion getMoneySumByAddress(GetMoneySumByAddressQuery query) {
        return queryGateway.query(
                        new QueryMoneySumByAddress(query.getAddress()),
                        MoneySumByRegion.class
                )
                .exceptionally(ex -> {
                    throw new RuntimeException("쿼리 처리 오류: " + ex.getMessage(), ex);
                })
                .join();
    }

    @Override
    public MoneySumByRegion getDailyMoneySumByAddress(GetDailyMoneySumByAddressQuery query) {
        return queryGateway.query(
                        new QueryDailyMoneySumByAddress(query.getAddress(), query.getDate()),
                        MoneySumByRegion.class
                )
                .exceptionally(ex -> {
                    throw new RuntimeException("쿼리 처리 오류: " + ex.getMessage(), ex);
                })
                .join();
    }

    @Override
    public List<MoneySumByMembership> getTopMoneySumByMembership(GetTopMoneySumByMembershipQuery query) {
        MoneySumByMembershipsResult result = queryGateway.query(
                        new QueryTopMoneySumByMembership(query.getAddress(), query.getFetchSize()),
                        MoneySumByMembershipsResult.class
                )
                .exceptionally(ex -> {
                    throw new RuntimeException("쿼리 처리 오류: " + ex.getMessage(), ex);
                })
                .join();
        return result.moneySumByMemberships();
    }
}
