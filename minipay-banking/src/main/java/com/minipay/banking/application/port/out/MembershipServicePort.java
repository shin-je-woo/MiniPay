package com.minipay.banking.application.port.out;

import java.util.List;
import java.util.UUID;

public interface MembershipServicePort {
    boolean isValidMembership (UUID membershipId);
    List<UUID> getMembershipIdsByAddress(String address);
}
