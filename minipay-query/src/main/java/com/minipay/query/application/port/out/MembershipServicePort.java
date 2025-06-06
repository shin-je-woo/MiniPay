package com.minipay.query.application.port.out;

import java.util.List;
import java.util.UUID;

public interface MembershipServicePort {
    MembershipInfo getMembershipInfo(UUID membershipId);
    List<UUID> getMembershipIdsByAddress(String address);
}
