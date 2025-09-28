package com.vemproazul.controller;

import com.vemproazul.entity.User;
import com.vemproazul.service.UserService;
import com.vemproazul.security.JwtUtil;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String senha = loginData.get("senha");
        Map<String, String> response = new HashMap<>();

        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getSenha().equals(senha)) {
            String token = jwtUtil.generateToken(email);
            response.put("token", token);
        } else {
            response.put("error", "Credenciais inválidas");
        }
        return response;
    }
}
