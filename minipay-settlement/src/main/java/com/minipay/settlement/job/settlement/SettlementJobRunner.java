package com.minipay.settlement.job.settlement;

import com.minipay.settlement.job.common.JobDefinition;
import com.minipay.settlement.job.common.JobRunner;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SettlementJobRunner extends JobRunner {

    public SettlementJobRunner(Scheduler scheduler) {
        super(scheduler);
    }

    @Override
    protected JobDefinition generateJobDefinition() {
        return new JobDefinition(
                SettlementJobLauncher.class,
                SettlementJobProvider.JOB_NAME,
                SettlementJobProvider.JOB_NAME + "Group",
                "0 0 9 L * ?"
        );
    }
}
