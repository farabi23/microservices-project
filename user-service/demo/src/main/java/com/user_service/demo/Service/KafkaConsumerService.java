package com.user_service.demo.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "user-events", groupId = "user-group")
    public void listen(String message) {
        System.out.println("Received Kafka Message: " + message);
    }
}
