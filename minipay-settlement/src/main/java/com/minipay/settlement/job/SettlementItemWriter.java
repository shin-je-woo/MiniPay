package com.minipay.settlement.job;

import com.minipay.settlement.port.out.BankingServicePort;
import com.minipay.settlement.port.out.PaymentServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component(SettlementJobProvider.JOB_NAME + "Writer")
@StepScope
public class SettlementItemWriter implements ItemWriter<SettlementResult> {

    private final BankingServicePort bankingServicePort;
    private final PaymentServicePort paymentServicePort;
    private final RetryTemplate retryTemplate;

    public SettlementItemWriter(BankingServicePort bankingServicePort, PaymentServicePort paymentServicePort) {
        this.bankingServicePort = bankingServicePort;
        this.paymentServicePort = paymentServicePort;
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(10);
        retryTemplate.setRetryPolicy(retryPolicy);
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(1000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        this.retryTemplate = retryTemplate;
    }

    /**
     * 정산된 금액은 미니페이 계좌에서 고객 계좌로 이체한다.
     * 전표는 정산 완료처리로 변경한다.
     */
    @Override
    public void write(Chunk<? extends SettlementResult> chunk) {
        chunk.getItems().forEach(settlementResult -> {
            retryTemplate.execute(retryContext -> bankingServicePort.transferSettlementAmount(
                    settlementResult.bankAccountId(),
                    settlementResult.bankName(),
                    settlementResult.bankAccountNumber(),
                    settlementResult.settlementAmount()
            ), retryContext -> {
                log.error("정산 금액 이체에 실패했습니다. paymentId = {}, settlementAmount = {}, bankAccountId = {}",
                        settlementResult.paymentId(), settlementResult.settlementAmount(), settlementResult.bankAccountId());
                throw new IllegalStateException("정산 금액 이체에 실패했습니다.", retryContext.getLastThrowable());
            });

            retryTemplate.execute(retryContext -> paymentServicePort.markPaymentSettled(
                    settlementResult.paymentId(),
                    settlementResult.settlementAmount(),
                    settlementResult.feeAmount()
            ), retryContext -> {
                log.error("전표 정산 완료처리에 실패했습니다. paymentId = {}, settlementAmount = {}",
                        settlementResult.paymentId(), settlementResult.settlementAmount());
                throw new IllegalStateException("전표 정산 완료처리에 실패했습니다.", retryContext.getLastThrowable());
            });
        });
    }
}
