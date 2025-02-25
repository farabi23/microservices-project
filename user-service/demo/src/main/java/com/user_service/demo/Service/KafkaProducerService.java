package com.user_service.demo.Service;


import com.user_service.demo.Dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "user-events";

    @Autowired
    private KafkaTemplate<String, NotificationDTO> kafkaTemplate;

    public void sendMessage(NotificationDTO notification) {
        kafkaTemplate.send(TOPIC, notification);
    }

}
