package com.minipay.common.aop;

import com.minipay.common.constants.Topic;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Before("execution(* com.minipay.*.adapter.in.web.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        kafkaTemplate.send(
                Topic.LOGGING,
                "logging",
                methodName + " was called."
        );
    }
}
