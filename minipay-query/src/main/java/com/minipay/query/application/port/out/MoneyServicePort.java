package com.minipay.query.application.port.out;

import java.util.List;
import java.util.UUID;

public interface MoneyServicePort {
    List<MoneyInfo> getMoneyInfosByMembershipIds(List<UUID> membershipIds);
}
