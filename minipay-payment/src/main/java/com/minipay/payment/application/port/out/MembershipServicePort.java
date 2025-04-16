package com.minipay.payment.application.port.out;

import java.util.UUID;

public interface MembershipServicePort {
    boolean isValidMembership(UUID membershipId);
}
