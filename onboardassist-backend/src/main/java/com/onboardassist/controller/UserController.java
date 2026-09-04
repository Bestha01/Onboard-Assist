package com.onboardassist.controller;

import com.onboardassist.entity.User;
import com.onboardassist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return ResponseEntity.ok(Map.of(
                "name", user.getName(),
                "email", user.getEmail(),
                "role", user.getRole()
            ));
        }
        return ResponseEntity.notFound().build();
    }
}
