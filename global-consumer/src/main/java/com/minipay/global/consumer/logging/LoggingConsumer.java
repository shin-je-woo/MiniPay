package com.minipay.global.consumer.logging;

import com.minipay.common.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingConsumer {

    @KafkaListener(
            topics = {EventType.LOGGING},
            groupId = "global-consumer.logging",
            concurrency = "3"
    )
    public void listen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        String message = record.value();
        log.info("Received message: {}", message);

        // TODO 수동커밋이어도 멱등하게 처리됨은 보장되지 않는다. 커밋되지 않은 상태로 리밸런싱이 발생하거나, 서비스를 재시작하면 동일한 메시지가 중복 처리될 수 있다.
        acknowledgment.acknowledge();
    }
}
