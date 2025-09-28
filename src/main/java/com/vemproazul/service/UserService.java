package com.vemproazul.service;

import com.vemproazul.entity.User;
import com.vemproazul.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public User saveUser(User user) { return userRepository.save(user); }

    public Optional<User> findByEmail(String email) { return userRepository.findByEmail(email); }

    public Optional<User> findById(Long id) { return userRepository.findById(id); }
}
