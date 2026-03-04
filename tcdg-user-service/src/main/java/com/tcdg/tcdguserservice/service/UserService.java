package com.tcdg.tcdguserservice.service;

import com.tcdg.tcdguserservice.model.User;
import com.tcdg.tcdguserservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsersByTenant(UUID tenantId) {
        return userRepository.findByTenantId(tenantId);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}