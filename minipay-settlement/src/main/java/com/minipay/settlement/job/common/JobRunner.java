package com.minipay.settlement.job.common;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.TimeZone;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class JobRunner implements ApplicationRunner {

    private final Scheduler scheduler;

    @Override
    public final void run(final ApplicationArguments args) {
        final JobDefinition jobDefinition = generateJobDefinition();

        final JobDetail jobDetail = buildJobDetail(jobDefinition);
        final Trigger trigger = buildJobTrigger(jobDefinition, jobDetail);

        scheduleJobSafely(jobDetail, trigger);
    }

    protected abstract JobDefinition generateJobDefinition();

    private JobDetail buildJobDetail(final JobDefinition jobDefinition) {
        return JobBuilder.newJob(jobDefinition.jobClass())
                .withIdentity(jobDefinition.jobKey())
                .storeDurably(true)
                .build();
    }

    private Trigger buildJobTrigger(final JobDefinition jobDefinition, final JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobDefinition.triggerName(), jobDefinition.triggerGroup())
                .forJob(jobDetail)
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(jobDefinition.cron())
                                .inTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
                )
                .build();
    }

    private void scheduleJobSafely(final JobDetail jobDetail, final Trigger trigger) {
        try {
            final JobKey jobKey = jobDetail.getKey();
            final TriggerKey triggerKey = trigger.getKey();

            scheduler.addJob(jobDetail, true);

            if (scheduler.checkExists(triggerKey)) {
                scheduler.rescheduleJob(triggerKey, trigger);
                log.info("Trigger rescheduled for job: {}", jobKey.getName());
            } else {
                scheduler.scheduleJob(trigger);
                log.info("Trigger scheduled for job: {}", jobKey.getName());
            }
        } catch (SchedulerException e) {
            log.error("Error scheduling/rescheduling job {}: {}", jobDetail.getKey().getName(), e.getMessage(), e);
        }
    }
}