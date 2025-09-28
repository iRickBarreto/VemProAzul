package com.vemproazul.controller;

import com.vemproazul.entity.User;
import com.vemproazul.service.UserService;
import com.vemproazul.security.JwtUtil;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String senha = loginData.get("senha");

        Optional<User> userOpt = userService.findByEmail(email);
        Map<String, String> response = new HashMap<>();

        if (userOpt.isPresent() && userOpt.get().getSenha().equals(senha)) {
            String token = jwtUtil.generateToken(email);
            response.put("token", token);
        } else {
            response.put("error", "Credenciais inválidas");
        }
        return response;
    }
}
