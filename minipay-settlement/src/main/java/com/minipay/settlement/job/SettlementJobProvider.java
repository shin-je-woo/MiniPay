package com.minipay.settlement.job;

import com.minipay.settlement.port.out.PaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SettlementJobProvider {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final SettlementItemReader settlementItemReader;

    private static final int CHUNK_SIZE = 100;
    public static final String JOB_NAME = "settlementJob";

    @Bean(name = JOB_NAME)
    public Job job() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(step())
                .build();
    }

    @JobScope
    @Bean(name = JOB_NAME + "Step")
    public Step step() {
        return new StepBuilder(JOB_NAME + "Step", jobRepository)
                .<PaymentInfo, PaymentInfo>chunk(CHUNK_SIZE, transactionManager)
                .reader(settlementItemReader)
                .processor(processor())
                .writer(writer())
                .build();
    }

    @StepScope
    @Bean(name = JOB_NAME + "Processor")
    public FunctionItemProcessor<PaymentInfo, PaymentInfo> processor(
    ) {
        return new FunctionItemProcessor<>(paymentInfo -> paymentInfo);
    }

    @StepScope
    @Bean(name = JOB_NAME + "Writer")
    public ItemWriter<PaymentInfo> writer() {
        return chunk -> {
            final List<? extends PaymentInfo> paymentInfos = chunk.getItems();
            paymentInfos.forEach(System.out::println);
        };
    }
}
