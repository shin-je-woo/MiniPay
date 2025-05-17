package com.minipay.query.application.port.in;

import com.minipay.query.domain.MoneySumByMembership;
import com.minipay.query.domain.MoneySumByRegion;

import java.util.List;

public interface GetMoneySumQueryUseCase {
    MoneySumByRegion getMoneySumByAddress(GetMoneySumByAddressQuery query);
    MoneySumByRegion getDailyMoneySumByAddress(GetDailyMoneySumByAddressQuery query);
    List<MoneySumByMembership> getTopMoneySumByMembership(GetTopMoneySumByMembershipQuery query);
}
