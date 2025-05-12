package com.minipay.query.application.port.out;

import java.util.List;
import java.util.UUID;

public interface MoneyServicePort {
    MoneyInfo getMoneyInfo(UUID memberMoneyId);
    MoneyInfo getMoneyInfoByMembershipId(UUID membershipId);
    List<MoneyInfo> getMoneyInfosByMembershipIds(List<UUID> membershipIds);
}
