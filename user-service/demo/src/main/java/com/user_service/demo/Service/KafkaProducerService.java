package com.user_service.demo.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_service.demo.Dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "user-events";


    private final KafkaTemplate<String, String> kafkaTemplate;


    private final ObjectMapper objectMapper;


    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(NotificationDTO notificationDTO) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(notificationDTO);
            kafkaTemplate.send(TOPIC, jsonMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
