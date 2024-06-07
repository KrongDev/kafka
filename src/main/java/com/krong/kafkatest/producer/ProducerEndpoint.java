package com.krong.kafkatest.producer;

import com.krong.kafkatest.config.KafkaProducerCluster;
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
    public void sendEvent(@RequestBody String message) {
        //
        log.info("send message: {}", message);
        cluster.sendMessage(message);
    }
}
