package com.minipay.query.adapter.in.axon.projection;

import com.minipay.query.application.port.out.*;
import com.minipay.saga.event.DepositFundSucceededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("moneySum-projection")
public class MoneySumProjection {

    private final InsertMoneySumPort insertMoneySumPort;
    private final MoneyServicePort moneyServicePort;
    private final MembershipServicePort membershipServicePort;

    /**
     * 멤버머니가 충전되면 멤버머니의 잔액 합을 DynamoDB에 업데이트한다.
     */
    @EventHandler
    public void on(DepositFundSucceededEvent event) {
        log.info("DepositFundSucceededEvent Handler");
        MoneyInfo moneyInfo = moneyServicePort.getMoneyInfo(event.memberMoneyId());
        MembershipInfo membershipInfo = membershipServicePort.getMembershipInfo(moneyInfo.membershipId());

        insertMoneySumPort.insertMoneyChange(
                membershipInfo.address(),
                event.amount(),
                LocalDate.now(),
                membershipInfo.membershipId()
        );

        insertMoneySumPort.upsertDailySummary(
                membershipInfo.address(),
                event.amount(),
                LocalDate.now()
        );

        insertMoneySumPort.upsertTotalSummary(
                membershipInfo.address(),
                event.amount()
        );
    }
}
