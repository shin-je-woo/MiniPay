package com.minipay.settlement.job.settlement;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class SettlementJobLauncher extends QuartzJobBean {

    private final Job settlementJob;
    private final JobLauncher jobLauncher;

    @SneakyThrows
    @Override
    protected void executeInternal(@Nonnull JobExecutionContext context) throws JobExecutionException {
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.withDayOfMonth(1);
        LocalDate lastDay = now.withDayOfMonth(now.lengthOfMonth());

        final JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDate("settlementStartDate", firstDay)
                .addLocalDate("settlementEndDate", lastDay)
                .toJobParameters();

        jobLauncher.run(settlementJob, jobParameters);
    }
}
