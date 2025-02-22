package com.minipay.money.application.port.out;

import java.util.UUID;

public interface GetMembershipPort {
    boolean isValidMembership (UUID membershipId);
}
