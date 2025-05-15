package com.minipay.query.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetDailyMoneySumByAddressQuery extends SelfValidating<GetDailyMoneySumByAddressQuery> {

    @NotNull
    @NotBlank
    private final String address;

    @NotNull
    private final LocalDate date;

    public GetDailyMoneySumByAddressQuery(String address, LocalDate date) {
        this.address = address;
        this.date = date;

        this.validateSelf();
    }
}