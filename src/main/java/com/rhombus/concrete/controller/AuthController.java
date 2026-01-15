package com.rhombus.concrete.controller;

import com.rhombus.concrete.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"https://50f313082f2e.ngrok-free.app", "https://54e10639396f.ngrok-free.app", "https://77e1a13c7bc4.ngrok-free.app", "http://localhost:4200", "http://192.168.1.2:4200"}, allowCredentials = "true")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        Map<String, Object> result = authService.login(username, password);
        return ResponseEntity.ok(result);
    }
}



