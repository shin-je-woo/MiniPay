package com.minipay.settlement.job.settlement;

import com.minipay.settlement.port.out.PaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.Assert;

@Configuration
@RequiredArgsConstructor
public class SettlementJobProvider {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final SettlementItemReader settlementItemReader;
    private final SettlementItemProcessor settlementItemProcessor;
    private final SettlementItemWriter settlementItemWriter;

    private static final int CHUNK_SIZE = 100;
    public static final String JOB_NAME = "settlementJob";

    @Bean(name = JOB_NAME)
    public Job job() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(step())
                .validator(validator())
                .build();
    }

    @JobScope
    @Bean(name = JOB_NAME + "Step")
    public Step step() {
        return new StepBuilder(JOB_NAME + "Step", jobRepository)
                .<PaymentInfo, SettlementResult>chunk(CHUNK_SIZE, transactionManager)
                .reader(settlementItemReader)
                .processor(settlementItemProcessor)
                .writer(settlementItemWriter)
                .build();
    }

    @Bean
    public JobParametersValidator validator() {
        return parameters -> {
            Assert.notNull(parameters, "JobParameters must not be null");
            Assert.notNull(parameters.getLocalDate("settlementStartDate"),
                    "settlementStartDate parameter must not be null");
            Assert.notNull(parameters.getLocalDate("settlementEndDate"),
                    "settlementEndDate parameter must not be null");
        };
    }
}
