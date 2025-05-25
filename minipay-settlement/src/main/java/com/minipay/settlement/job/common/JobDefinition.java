package com.minipay.settlement.job.common;

import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;

public record JobDefinition(
        Class<? extends QuartzJobBean> jobClass,
        String jobName,
        String jobGroup,
        String cron
) {
    public JobKey jobKey() {
        return new JobKey(jobName, jobGroup);
    }

    public String triggerName() {
        return jobName + "Trigger";
    }

    public String triggerGroup() {
        return jobGroup + "Trigger";
    }
}