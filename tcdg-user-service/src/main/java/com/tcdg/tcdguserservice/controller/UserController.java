package com.tcdg.tcdguserservice.controller;

import com.tcdg.tcdguserservice.model.User;
import com.tcdg.tcdguserservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/tenant/{tenantId}")
    public List<User> getUsersByTenant(@PathVariable UUID tenantId) {
        return userService.getUsersByTenant(tenantId);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }
}