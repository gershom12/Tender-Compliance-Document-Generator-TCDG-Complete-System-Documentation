package com.tcdg.tcdgauthservice.service;

import com.tcdg.tcdgauthservice.kafka.KafkaProducerService;
import com.tcdg.tcdgauthservice.model.User;
import com.tcdg.tcdgauthservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final KafkaProducerService kafkaProducer;

    public User signup(String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        userRepository.save(user);

        // Publish Kafka event
        kafkaProducer.publishUserCreatedEvent(username);

        return user;
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return username; // will be used to generate JWT in controller
    }
}