package com.tcdg.tcdguserservice.kafka;

import com.tcdg.tcdguserservice.model.User;
import com.tcdg.tcdguserservice.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserEventConsumer {

    private final UserRepository userRepository;

    public UserEventConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "user-events", groupId = "tcdg-user-group")
    public void handleUserCreatedEvent(String message) {
        System.out.println("Received user event: " + message);

        // Example message: "UserCreated:username"
        if (message.startsWith("UserCreated:")) {
            String username = message.split(":")[1];

            // Create user in user-service DB
            User user = User.builder()
                    .id(UUID.randomUUID())
                    .tenantId(UUID.randomUUID()) // TODO: map real tenant ID from Auth-Service
                    .username(username)
                    .passwordHash("N/A") // actual password not stored here
                    .role("ROLE_USER")
                    .enabled(true)
                    .build();

            userRepository.save(user);
            System.out.println("User saved in user-service: " + username);
        }
    }
}