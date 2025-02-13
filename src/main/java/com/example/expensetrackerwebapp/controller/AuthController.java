package com.example.expensetrackerwebapp.controller;

import com.example.expensetrackerwebapp.dto.AuthRequest;
import com.example.expensetrackerwebapp.dto.AuthResponse;
import com.example.expensetrackerwebapp.model.User;
import com.example.expensetrackerwebapp.security.JwtUtil;
import com.example.expensetrackerwebapp.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        userService.registerUser(request.getEmail(), request.getPassword());
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        User user = userService.findByEmail(request.getEmail()).orElseThrow(() ->
                new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}
