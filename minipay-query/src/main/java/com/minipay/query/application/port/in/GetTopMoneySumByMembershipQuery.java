package com.minipay.query.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetTopMoneySumByMembershipQuery extends SelfValidating<GetTopMoneySumByMembershipQuery> {

    @NotNull
    @NotBlank
    private final String address;

    @NotNull
    private final Integer fetchSize;

    public GetTopMoneySumByMembershipQuery(String address, Integer fetchSize) {
        this.address = address;
        this.fetchSize = fetchSize;

        this.validateSelf();
    }
}