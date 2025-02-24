package com.user_service.demo.Service;

import com.user_service.demo.Dto.NotificationDTO;
import com.user_service.demo.Entity.User;
import com.user_service.demo.Repository.UserRepository;
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

    public User saveUser(User user) {
        //  Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //  Save user in DB
        User savedUser = userRepository.save(user);

        // notification dto
        NotificationDTO notificationDTO = new NotificationDTO(savedUser.getId(), "User created: " + savedUser.getUsername());

        //  Publish event to Kafka
        kafkaProducerService.sendMessage(notificationDTO);

        return savedUser;
    }

    @Cacheable(cacheNames = "users", key = "#username")
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);

        NotificationDTO notificationDTO = new NotificationDTO(id, "User deleted: " + id);

        kafkaProducerService.sendMessage(notificationDTO);
    }
}
