package com.tcdg.tcdguserservice.repository;

import com.tcdg.tcdguserservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    List<User> findByTenantId(UUID tenantId);
}