package com.minipay.query.adapter.out.service.membership;

import java.util.List;

public record MembershipByAddressResponse(
        List<MembershipResponse> memberships
) {
}
