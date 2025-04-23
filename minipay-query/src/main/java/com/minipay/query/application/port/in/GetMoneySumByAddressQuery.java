package com.minipay.query.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetMoneySumByAddressQuery extends SelfValidating<GetMoneySumByAddressQuery> {

    @NotNull
    @NotBlank
    private final String address;

    public GetMoneySumByAddressQuery(String address) {
        this.address = address;

        this.validateSelf();
    }
}