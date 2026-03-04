package com.tcdg.tcdgauthservice.controller;

import com.tcdg.tcdgauthservice.security.JwtUtil;
import com.tcdg.tcdgauthservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam(defaultValue = "ROLE_USER") String role) {
        authService.signup(username, password, role);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        authService.login(username, password);
        return jwtUtil.generateToken(username);
    }
}