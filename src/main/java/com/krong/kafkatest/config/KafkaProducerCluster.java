package com.krong.kafkatest.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerCluster {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String topicName;

    public void sendMessage(String payload) {

        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);
        future.whenComplete((r, e) -> {
            if (e == null) {
                log.info("Producer: success >> message: {}, offset: {}", r.getProducerRecord().value(), r.getRecordMetadata().offset());
            } else {
                log.error("producer: failure >> message: {}", e.getMessage());
            }
        });
    }
}
