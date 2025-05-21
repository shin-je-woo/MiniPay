package com.minipay.settlement.job;

import com.minipay.settlement.port.out.PaymentInfo;
import com.minipay.settlement.port.out.PaymentServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component(SettlementJobProvider.JOB_NAME + "Reader")
@StepScope
@RequiredArgsConstructor
public class SettlementItemReader implements ItemReader<PaymentInfo>, ItemStream {

    private final PaymentServicePort paymentServicePort;

    private static final int PAGE_SIZE = 10;
    private int currentPage = 0;
    private boolean hasMorePayments = true;
    private Iterator<PaymentInfo> currentPayments = Collections.emptyIterator();

    @Override
    public PaymentInfo read() {
        if (!hasMorePayments) {
            currentPage = 0;
            return null;
        }

        if (!currentPayments.hasNext()) {
            getNextPageOfPayments();
            if (!hasMorePayments) {
                return null;
            }
        }

        return currentPayments.next();
    }

    private void getNextPageOfPayments() {
        List<PaymentInfo> paymentInfos = paymentServicePort.getUnpaidPaymentsPaged(currentPage, PAGE_SIZE);

        if (CollectionUtils.isEmpty(paymentInfos)) {
            hasMorePayments = false;
            return;
        }

        currentPayments = paymentInfos.iterator();
        currentPage++;
    }

    // ItemStream 구현 메서드들 (재시작 기능을 위한)
    @Override
    public void open(ExecutionContext executionContext) {
        if (executionContext.containsKey("current.page")) {
            currentPage = executionContext.getInt("current.page");
            hasMorePayments = true;
        }
    }

    @Override
    public void update(ExecutionContext executionContext) {
        executionContext.putInt("current.page", currentPage);
    }

}
