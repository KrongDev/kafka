package com.krong.kafkatest.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krong.kafkatest.config.KafkaProducerCluster;
import com.krong.kafkatest.model.KafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/producer")
@RequiredArgsConstructor
public class ProducerEndpoint {
    //
    private final KafkaProducerCluster cluster;

    @PostMapping("/message")
    public void sendEvent(@RequestBody KafkaEvent message) throws JsonProcessingException {
        //
        message.validate();
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("send message: {}", objectMapper.writeValueAsString(message));
        cluster.sendMessage(message);
    }
}
