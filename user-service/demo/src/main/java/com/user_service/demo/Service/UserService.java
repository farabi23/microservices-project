package com.user_service.demo.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_service.demo.Dto.NotificationDTO;
import com.user_service.demo.Entity.User;
import com.user_service.demo.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KafkaProducerService kafkaProducerService;
    @Autowired
    private ObjectMapper objectMapper;

    public User saveUser(User user) throws JsonProcessingException {
        //  Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //  Save user in DB
        User savedUser = userRepository.save(user);

        // notification dto
        NotificationDTO notificationDTO = new NotificationDTO(savedUser.getId(), savedUser.getUsername(),
                "User created: " + savedUser.getUsername());

        //  Publish event to Kafka
        kafkaProducerService.sendMessage(notificationDTO);

        return savedUser;
    }

    @Cacheable(cacheNames = "users", key = "#username")
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + email + " not found")
        );
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);

        User user = userRepository.findById(id).orElse(null);

        NotificationDTO notificationDTO = new NotificationDTO(id, user.getUsername(),
                "User deleted: " + id);

        kafkaProducerService.sendMessage(notificationDTO);
    }
}
