package com.minipay.banking.adapter.out.service;

import java.util.List;

public record MembershipByAddressResponse(
        List<MembershipResponse> memberships
) {
}