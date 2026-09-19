package com.aribnb.backend.controller;

import com.aribnb.backend.dto.AuthResponse;
import com.aribnb.backend.dto.LoginRequest;
import com.aribnb.backend.dto.RegisterRequest;
import com.aribnb.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
@CrossOrigin("*")
public class AuthController {
    private final UserService userService;

    @RequestMapping("/register")
    public ResponseEntity<AuthResponse> register (@RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }
    @RequestMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(userService.login(request));
    }
}
